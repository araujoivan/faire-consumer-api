/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exercise.faire.model;

import java.time.LocalDateTime;

/**
 *    
 * @author eder
 */
public class BackOrder {
    
    private int available_quantity;
    private boolean discontinued =  false;
    private LocalDateTime backordered_until;
    
    public BackOrder(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public LocalDateTime getBackordered_until() {
        return backordered_until;
    }

    public void setBackordered_until(LocalDateTime backordered_until) {
        this.backordered_until = backordered_until;
    }  
}
