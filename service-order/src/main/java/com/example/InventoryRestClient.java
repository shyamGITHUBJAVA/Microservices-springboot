//package com.example;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class InventoryRestClient {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public List<InventoryResponse> checkInventory(List<String> codes) {
//        String url = "http://localhost:8082/api/inventory?productCodes=" + String.join(",", codes);
//        InventoryResponse[] response = restTemplate.getForObject(url, InventoryResponse[].class);
//        return Arrays.asList(response);
//    }
//}
//
