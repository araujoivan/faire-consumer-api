package com.exercise.faire.service;

import com.exercise.faire.constant.OrderStatus;
import com.exercise.faire.model.Order;
import com.exercise.faire.model.ProductOption;
import java.util.List;

/**
 *
 * @author eder
 */
public interface StatisticService {
    
    public ProductOption getTheBestSellingProductOption(List<Order> orders, List<ProductOption> options);
    
    public Order getTheLargestOrderDollarAmount(List<Order> orders);
    
    public OrderStatus getTheStateOfTheMostOrders(List<Order> orders);
    
}
