package com.example;



import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {


    private final List<Inventory> dummyInventory = new ArrayList<>(Arrays.asList(
            new Inventory("iphone_13", 5),
            new Inventory("samsung_s22", 10),
            new Inventory("pixel_6", 0)
    ));

    @GetMapping("/hello")
    public String helloMethod(){
        return "hello from app and kubernetes is working fine";
    }

    @PostMapping("/reserve")
    public boolean reserveStock(@RequestBody List<OrderLineItemsDto> items) {
        for (OrderLineItemsDto item : items) {
            Optional<Inventory> inventoryOpt = dummyInventory.stream()
                    .filter(inv -> inv.getSkuCode().equals(item.getSkuCode()))
                    .findFirst();

            if (inventoryOpt.isEmpty() || inventoryOpt.get().getQuantity() < item.getQuantity()) {
                return false;
            }
        }

        // Reserve stock
        for (OrderLineItemsDto item : items) {
            dummyInventory.stream()
                    .filter(inv -> inv.getSkuCode().equals(item.getSkuCode()))
                    .findFirst()
                    .ifPresent(inv -> inv.setQuantity(inv.getQuantity() - item.getQuantity()));
        }

        return true;
    }

    @PostMapping("/release")
    public void releaseStock(@RequestBody List<OrderLineItemsDto> items) {
        for (OrderLineItemsDto item : items) {
            dummyInventory.stream()
                    .filter(inv -> inv.getSkuCode().equals(item.getSkuCode()))
                    .findFirst()
                    .ifPresent(inv -> inv.setQuantity(inv.getQuantity() + item.getQuantity()));
        }
    }

}
