/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exercise.faire.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 *
 * @author eder
 */
public class Inventory {
    
    private String sku;
    private int current_quantity;
    
//    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSSXXX")
//    private LocalDateTime backordered_until;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCurrent_quantity() {
        return current_quantity;
    }

    public void setCurrent_quantity(int current_quantity) {
        this.current_quantity = current_quantity;
    }

//    public LocalDateTime getBackordered_until() {
//        return backordered_until;
//    }
//
//    public void setBackordered_until(LocalDateTime backordered_until) {
//        this.backordered_until = backordered_until;
//    } 
}
