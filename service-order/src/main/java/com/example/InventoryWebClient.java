//package com.example;
//
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class InventoryWebClient {
//
//    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8082").build();
//
//    public Mono<List<InventoryResponse>> checkInventory(List<String> codes) {
//        String query = String.join(",", codes);
//        return webClient.get()
//                .uri("/api/inventory?productCodes={codes}", query)
//                .retrieve()
//                .bodyToFlux(InventoryResponse.class)
//                .collectList();
//    }
//}
