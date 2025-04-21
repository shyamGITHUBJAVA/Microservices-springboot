//package com.example;
//
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/inventory")
//public class InventoryController {
//
//    private final List<Inventory> dummyInventory = Arrays.asList(
//            new Inventory("iphone_13", 5),
//            new Inventory("samsung_s22", 10),
//            new Inventory("pixel_6", 0)
//    );
//
//    @GetMapping
//    public List<Inventory> isInStock(@RequestParam List<String> skuCode) {
//        return dummyInventory.stream()
//                .filter(inv -> skuCode.contains(inv.getSkuCode()))
//                .collect(Collectors.toList());
//    }
//}
