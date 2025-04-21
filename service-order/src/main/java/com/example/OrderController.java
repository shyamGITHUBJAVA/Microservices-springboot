package com.example;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
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

//

//    @Autowired
//    private InventoryFeignClient inventoryFeignClient;
//    @PostMapping
//    public String placeOrder(@RequestBody OrderRequest orderRequest) {
//        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList().stream()
//                .map(OrderLineItemsDto::getSkuCode)
//                .toList();
//
//        List<InventoryResponse> inventoryResponses = inventoryFeignClient.checkInventory(skuCodes);
//
//        boolean allInStock = orderRequest.getOrderLineItemsDtoList().stream()
//                .allMatch(orderItem ->
//                        inventoryResponses.stream()
//                                .filter(inv -> inv.getSkuCode().equals(orderItem.getSkuCode()))
//                                .findFirst()
//                                .map(inv -> inv.getQuantity() >= orderItem.getQuantity())
//                                .orElse(false)
//                );
//
//        return allInStock ? "Order Placed Successfully" : "One or more items are out of stock";
//    }


    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        String orderJson = convertOrderToJson(orderRequest);  // Assume you have a method to convert the order to JSON
        orderProducer.sendOrderToInventory(orderJson);
        return "Order placed and sent to Inventory Service for stock check";
    }

    private String convertOrderToJson(OrderRequest orderRequest) {
        try {
            return objectMapper.writeValueAsString(orderRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert order to JSON", e);
        }
    }

}
