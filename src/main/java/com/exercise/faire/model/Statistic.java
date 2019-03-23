package com.exercise.faire.model;

import com.exercise.faire.constant.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import static java.util.Objects.nonNull;

/**
 *
 * @author eder
 */
public class Statistic {
    
    @JsonIgnore
    private ProductOption bestSeller;
    
    @JsonIgnore
    private Order largestDollarAmount;
    
    @JsonIgnore
    private OrderStatus orderStatus;
    
    private String bestSellerProductOption;
    private String largestDollarAmountOrder;
    private String mostStatus;

    public void setBestSeller(ProductOption bestSeller) {
        this.bestSeller = bestSeller;
        if(nonNull(bestSeller)) {
           bestSellerProductOption = "The Best Seller product option is ".concat(bestSeller.getName());
        }
    }

    public void setLargestDollarAmount(Order largestDollarAmount) {
        
        this.largestDollarAmount = largestDollarAmount;
        if(nonNull(largestDollarAmount)) {
            
            final Integer amount = largestDollarAmount.getItems().stream()
                    .map(item -> item.getPrice_cents() * item.getQuantity()).reduce(0, (el1, el2) -> el1 + el2);
            
            largestDollarAmountOrder = "The largest dollar amount orders is ".concat(largestDollarAmount.getId())
                    .concat(" with a total of ").concat(String.valueOf(largestDollarAmount.getItems().size()))
                    .concat(" ordered items and a total amount of $ ").concat(String.valueOf(BigDecimal.valueOf(amount / 100)));
                    
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        if(nonNull(orderStatus)) {
            mostStatus = "The majority of orders are in ".concat(orderStatus.name()).concat(" state.");
        }
    }

    public String getBestSellerProductOption() {
        return bestSellerProductOption;
    }

    public void setBestSellerProductOption(String bestSellerProductOption) {
        this.bestSellerProductOption = bestSellerProductOption;
    }

    public String getLargestDollarAmountOrder() {
        return largestDollarAmountOrder;
    }

    public void setLargestDollarAmountOrder(String largestDollarAmountOrder) {
        this.largestDollarAmountOrder = largestDollarAmountOrder;
    }

    public String getMostStatus() {
        return mostStatus;
    }

    public void setMostStatus(String mostStatus) {
        this.mostStatus = mostStatus;
    }
    
    
}
