package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public String placeOrder(OrderRequest request) {
        // Step 1: Check and reserve inventory
        boolean inventoryAvailable = restTemplate.postForObject(
                "http://localhost:8082/api/inventory/reserve",
                request.getOrderLineItemsDtoList(),
                Boolean.class
        );

        if (!inventoryAvailable) {
            return "Inventory not available";
        }

        // Step 2: Convert DTOs to entities
        List<OrderLineItems> orderLineItems = convertToOrderLineItems(request.getOrderLineItemsDtoList());

        // Step 3: Save order
        Order order = new Order();
        order.setStatus("PLACED");
        order.setOrderLineItems(orderLineItems);

        try {
            if (true) {
                throw new DataAccessException("Simulated failure") {};
            }
            orderRepository.save(order);
        } catch (DataAccessException ex) {
            // Compensation: release reserved inventory
            restTemplate.postForEntity(
                    "http://localhost:8082/api/inventory/release",
                    request.getOrderLineItemsDtoList(),
                    Void.class
            );
            return "Order failed to save, reserved inventory released";
        }

        return "Order placed successfully";
    }

    // Helper method to convert DTOs
    private List<OrderLineItems> convertToOrderLineItems(List<OrderLineItemsDto> orderLineItemsDtoList) {
        return orderLineItemsDtoList.stream()
                .map(dto -> {
                    OrderLineItems item = new OrderLineItems();
                    item.setSkuCode(dto.getSkuCode());
                    item.setQuantity(dto.getQuantity());
                    item.setPrice(dto.getPrice());
                    return item;
                })
                .collect(Collectors.toList());
    }
}

