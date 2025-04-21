package com.example;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @PostMapping
//    public String placeOrder(@RequestBody OrderRequest orderRequest) {
//        boolean allInStock = orderRequest.getOrderLineItemsDtoList().stream()
//                .allMatch(item -> {
//                    String url = "http://localhost:8082/api/inventory?skuCode=" + item.getSkuCode();
//                    InventoryResponse[] responses = restTemplate.getForObject(url, InventoryResponse[].class);
//                    return responses != null && responses.length > 0 && responses[0].getQuantity() >= item.getQuantity();
//                });
//
//        if (allInStock) {
//            return "Order Placed Successfully";
//        } else {
//            return "One or more items are out of stock";
//        }
//    }


    private final WebClient.Builder webClientBuilder;

    public OrderController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @PostMapping
    public Mono<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Create a list of Mono checks for each item in the order
        List<Mono<Boolean>> stockChecks = orderRequest.getOrderLineItemsDtoList().stream()
                .map(item -> {
                    String url = "http://localhost:8082/api/inventory?skuCode=" + item.getSkuCode();
                    // Make asynchronous request using WebClient
                    return webClientBuilder.build()
                            .get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(InventoryResponse[].class)
                            .map(responses -> responses != null && responses.length > 0 &&
                                    responses[0].getQuantity() >= item.getQuantity());
                })
                .toList();

        // Combine all the stock checks and verify that all are true (i.e., all items are in stock)
        return Mono.zip(stockChecks, results -> {
            boolean allInStock = true;
            for (Object result : results) {
                allInStock &= (Boolean) result;
            }
            return allInStock ? "Order Placed Successfully" : "One or more items are out of stock";
        });
    }
}
