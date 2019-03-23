package com.exercise.faire.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eder
 */
public class OrderPage {
    
    private List<Order> orders = new ArrayList();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
