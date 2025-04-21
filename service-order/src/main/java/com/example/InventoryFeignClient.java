package com.example;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory", url = "http://localhost:8082")
public interface InventoryFeignClient {

    @GetMapping("/api/inventory")
    List<InventoryResponse> checkInventory(@RequestParam("skuCode") List<String> skuCodes);
}
