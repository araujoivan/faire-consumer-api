package com.exercise.faire.service.impl;

import com.exercise.faire.constant.OrderStatus;
import com.exercise.faire.model.Order;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.service.StatisticService;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author eder
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Override
    public ProductOption getTheBestSellingProductOption(List<Order> orders, List<ProductOption> options) {

        final Map<String, Integer> productOptionMap = new HashMap();

        orders.forEach(order -> {
            order.getItems().forEach(item -> {

                if (productOptionMap.containsKey(item.getProduct_option_id())) {
                    Integer quantity = productOptionMap.get(item.getProduct_option_id());
                    productOptionMap.remove(item.getProduct_option_id());
                    productOptionMap.put(item.getProduct_option_id(), ++quantity);

                } else {
                    productOptionMap.put(item.getProduct_option_id(), 1);
                }
            });
        });

        final String option_id = getKeyFromMaxValue(productOptionMap);

        return options.stream().filter(option -> option.getId().equals(option_id)).findFirst().get();
    }

    @Override
    public Order getTheLargestOrderDollarAmount(List<Order> orders) {

        final Map<String, Integer> orderAmountOfMoneyMap = new HashMap();

        orders.forEach(order -> {

            final Integer amount = order.getItems().stream()
                    .map(item -> item.getPrice_cents() * item.getQuantity()).reduce(0, (el1, el2) -> el1 + el2);

            orderAmountOfMoneyMap.put(order.getId(), amount);
        });

        final String order_id = getKeyFromMaxValue(orderAmountOfMoneyMap);

        return orders.stream().filter(order -> order.getId().equals(order_id)).findFirst().get();
    }

    @Override
    public OrderStatus getTheStateOfTheMostOrders(List<Order> orders) {

        final Map<String, Integer> orderSateMap = new HashMap();

        orders.forEach(order -> {

            if (orderSateMap.containsKey(order.getState())) {
                Integer quantity = orderSateMap.get(order.getState());
                orderSateMap.remove(order.getState());
                orderSateMap.put(order.getState(), ++quantity);

            } else {
                orderSateMap.put(order.getState(), 1);
            }
        });
        
        final String key = getKeyFromMaxValue(orderSateMap);

        return OrderStatus.valueOf(key);
    }

    private String getKeyFromMaxValue(Map<String, Integer> map) {

        final LinkedHashMap<String, Integer> sortedMap = map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        final Map.Entry<String, Integer> entry = sortedMap.entrySet().iterator().next();

        return entry.getKey();
    }
}
