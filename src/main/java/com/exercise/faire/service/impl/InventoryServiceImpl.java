/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exercise.faire.service.impl;

import com.exercise.faire.model.Inventory;
import com.exercise.faire.model.Order;
import com.exercise.faire.model.OrderItem;
import com.exercise.faire.model.InventoryPage;
import com.exercise.faire.model.Product;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.service.InventoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    @Override
    public List<ProductOption> updateIventoryByProducts(List<Product> products) {
        
        final List<Inventory> inventories = new ArrayList();
        
        products.forEach(product -> {
            inventories.addAll(product.getOptions().stream().map(InventoryServiceImpl::mapToInventory).collect(Collectors.toList()));
        });
        
        return updateIventory(inventories);
    }
    
    @Override
    public List<ProductOption> updateIventoryByOrders(List<Order> ordersInProcessing) {
        
        final List<Inventory> inventories = new ArrayList();
         
        ordersInProcessing.forEach(order -> {            
            inventories.addAll(order.getItems().stream().map(InventoryServiceImpl::mapToInventory).collect(Collectors.toList()));            
        });
        
        return updateIventory(inventories);
    }
    
    private List<ProductOption> updateIventory(List<Inventory> inventories) {
       
        final String queryUrl = baseUrl.concat("products/options/inventory-levels");
        
        final InventoryPage pageInventory = new InventoryPage(inventories);

        final HttpEntity<InventoryPage> entity = new HttpEntity(pageInventory);       

        final ResponseEntity<Product> response = restTemplate.exchange(queryUrl, HttpMethod.PATCH, entity, Product.class);
        
        return response.getBody().getOptions();
        
    }
    
    private static Inventory mapToInventory(OrderItem orderItem) {
        
        final Inventory inventory = new Inventory();
        
        //TODO : Discovery how to decrement an inventory?
        inventory.setCurrent_quantity(-orderItem.getQuantity());
        inventory.setSku(orderItem.getSku());
        
        return inventory;
    }
    
    private static Inventory mapToInventory(ProductOption option) {
        
        final Inventory inventory = new Inventory();
        
        inventory.setCurrent_quantity(0);
        inventory.setSku(option.getSku());
        
        return inventory;
    }
}
