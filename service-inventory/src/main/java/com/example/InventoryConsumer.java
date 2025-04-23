//package com.example;
//
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class InventoryConsumer {
//
//    @KafkaListener(topics = "order_topic", groupId = "inventory-service-group")
//    public void consumeOrder(String orderJson) {
//        System.out.println("Received order: " + orderJson);
//
//        // Logic to check inventory and respond back with availability
//        // For now, just a simple message:
//        boolean isAvailable = checkInventory(orderJson);
//
//        // Logic to send response back, if necessary, could be implemented here
//        if (isAvailable) {
//            System.out.println("Inventory available for the order");
//        } else {
//            System.out.println("Inventory not available for the order");
//        }
//    }
//
//    private boolean checkInventory(String orderJson) {
//        // Inventory checking logic
//        return true;  // For now, we are always returning true
//    }
//}
