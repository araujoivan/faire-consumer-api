package com.exercise.faire.service;

import com.exercise.faire.model.Order;
import com.exercise.faire.model.Product;
import com.exercise.faire.model.ProductOption;
import java.util.List;

/**
 *
 * @author eder
 */
public interface InventoryService {
    
    public List<ProductOption> updateIventoryByProducts(List<Product> products);

    public List<ProductOption> updateIventoryByOrders(List<Order> ordersInProcessing);
    
}