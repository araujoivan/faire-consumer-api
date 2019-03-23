package com.exercise.faire.service.impl;

import com.exercise.faire.constant.OrderStatus;
import com.exercise.faire.model.BackOrder;
import com.exercise.faire.model.Order;
import com.exercise.faire.model.OrderItem;
import com.exercise.faire.model.OrderPage;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.service.OrderService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author eder
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    @Override
    public List<Order> getAllNewOrders() {

        // TODO: Verify the reason why the filter by excluded_states is not working
        /*final String queryUrl = baseUrl.concat("orders?excluded_states=")
                .concat(OrderStatus.getAllNonNewStatus())
                .concat("&limit=50&page="); */
        
        final String queryUrl = baseUrl.concat("orders?limit=50&page=");

        final List<Order> orders = new ArrayList();

        int pageNumber = 1;

        boolean hasResults = true;

        do {

            final ResponseEntity<OrderPage> response
                    = restTemplate.getForEntity(queryUrl.concat(String.valueOf(pageNumber++)), OrderPage.class);

            hasResults = nonNull(response.getBody())
                    && nonNull(response.getBody().getOrders())
                    && !response.getBody().getOrders().isEmpty();

            // TODO: Verify the reason why the filter by excluded_states is not working 
            if (hasResults) {
                orders.addAll(response.getBody().getOrders().stream()
                        .filter(order -> order.getState().equals(OrderStatus.NEW.name()))
                        .collect(Collectors.toList()));
            }

        } while (hasResults);

        return orders;
    } 
    
    private void createBackOrderList(Map<String, List<OrderItem>> backOrderMap) {
        
        backOrderMap.keySet().forEach(orderId -> {
            
            final String queryUrl = baseUrl.concat("orders/").concat(orderId).concat("/items/availability");
            
            final List<OrderItem> orderItems = backOrderMap.get(orderId);
            
            final Map<String, BackOrder> backorderMap = new HashMap();

            orderItems.forEach(orderItem -> {
                backorderMap.put(orderItem.getId(), new BackOrder(orderItem.getQuantity()));
            });
            
            final HttpEntity<Map<String, BackOrder>> entity = new HttpEntity(backOrderMap);  
        
            restTemplate.exchange(queryUrl, HttpMethod.POST, entity, Map.class);
            
        });   
    }
    
    private Order acceptOrder(Order order) {
        
        final String queryUrl = baseUrl.concat("orders/").concat(order.getId()).concat("/processing");
        
        final HttpEntity<Order> entity = new HttpEntity(order);  
        
        final ResponseEntity<Order> response  = restTemplate.exchange(queryUrl, HttpMethod.PUT, entity, Order.class);
        
        return response.getBody();
    }

    @Override
    public List<Order> executeOrdersByAvailabilityOfProductOptions(List<Order> orders, List<ProductOption> options) {
        
        final List<Order> ordersInProcess = new ArrayList(); 
        
        orders.forEach(order -> {
            
            Map<String, List<OrderItem>> unsatisfiedOrderItemsMap = getUnsatisfiedOrderItemsMap(order, options);
            
            if(unsatisfiedOrderItemsMap.isEmpty()) {
                 ordersInProcess.add(acceptOrder(order));
            } else {
                createBackOrderList(unsatisfiedOrderItemsMap);
            }
        });
        
        return ordersInProcess;
    }
  
    private Map<String, List<OrderItem>> getUnsatisfiedOrderItemsMap(Order order, List<ProductOption> options) {
        
        final Map<String, List<OrderItem>> backOrderMap = new HashMap();

        order.getItems().forEach(item -> {
            
            final ProductOption option = options.stream()
                    .filter(o -> o.getId().equals(item.getProduct_option_id()))
                    .findFirst()
                    .get();
            
            if(nonNull(option)) {
                // TODO: Verify if there are more business rules to implement here
                if(item.getQuantity() > option.getAvailable_quantity()) {
                    
                    final OrderItem backOrderItem = new OrderItem();
                    
                    //TODO: Verify if it makes sense request just the needed quantity of items?
                    backOrderItem.setQuantity(item.getQuantity() - option.getAvailable_quantity());
                    backOrderItem.setId(item.getId());
                    backOrderItem.setOrder_id(item.getOrder_id());
                    
                    if(backOrderMap.containsKey(order.getId())) {
                        backOrderMap.get(order.getId()).add(backOrderItem);
                    } else {
                       final List<OrderItem> backOrderItems = new ArrayList();
                       backOrderItems.add(backOrderItem);
                       backOrderMap.put(order.getId(), backOrderItems);
                    }
                }  
            }
        });
        
        return backOrderMap;
    }
}
