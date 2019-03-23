package com.exercise.faire.controller;

import com.exercise.faire.constant.OrderStatus;
import com.exercise.faire.model.Order;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.model.Statistic;
import com.exercise.faire.service.InventoryService;
import com.exercise.faire.service.OrderService;
import com.exercise.faire.service.ProductService;
import com.exercise.faire.service.StatisticService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StatisticService statisticService;

    @ApiOperation(value = "Consumes all orders with status NEW",
            notes = "Consumes all orders, accepting the order if there is inventory to fulfill the order and it is not already accepted, otherwise it marks the items that don’t have enough inventory as backordered and update the inventory levels of product options as each order is moved to processing.At the end is generate the following statistics: 1 - The best selling product option, 2 - The largest order by dollar amount, 3 - The state with the most orders",
            response = Statistic.class,
            responseContainer = "List")
    @GetMapping("/consume-all")
    public ResponseEntity consumeAllOrders() {

        final List<Order> orders = orderService.getAllNewOrders();

        if (orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        final List<ProductOption> options = productService.getProductOptionsByOrders(orders);
        final List<Order> ordersInProcessing = orderService.executeOrdersByAvailabilityOfProductOptions(orders, options);

        final Statistic statistic = getStatistic(orders, options);

        if (!ordersInProcessing.isEmpty()) {
            inventoryService.updateIventoryByOrders(ordersInProcessing);
        }

        return new ResponseEntity(statistic, HttpStatus.OK);
    }

    private Statistic getStatistic(List<Order> orders, List<ProductOption> options) {

        final Statistic statistic = new Statistic();

        final ProductOption bestSeller = statisticService.getTheBestSellingProductOption(orders, options);
        final Order largestDollarAmountOrder = statisticService.getTheLargestOrderDollarAmount(orders);
        final OrderStatus status = statisticService.getTheStateOfTheMostOrders(orders);

        statistic.setBestSeller(bestSeller);
        statistic.setLargestDollarAmount(largestDollarAmountOrder);
        statistic.setOrderStatus(status);

        return statistic;
    }

}
