package com.exercise.faire.service;

import com.exercise.faire.model.Order;
import com.exercise.faire.model.ProductOption;
import java.util.List;

/**
 *
 * @author eder
 */
public interface OrderService {
    
    public List<Order> getAllNewOrders();

    public List<Order> executeOrdersByAvailabilityOfProductOptions(List<Order> orders, List<ProductOption> options);

}
