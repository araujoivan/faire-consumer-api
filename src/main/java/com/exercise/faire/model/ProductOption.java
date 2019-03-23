package com.exercise.faire.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 *20190314T000915.000Z
 * @author eder
 */
public class ProductOption {
    
    private String id;
    private String sku;
    private String product_id;
    private String name;
    private boolean active;
    private int available_quantity;
    
    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSSXXX")
    private LocalDateTime backordered_until;
    
    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSSXXX")
    private LocalDateTime  created_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getBackordered_until() {
        return backordered_until;
    }

    public void setBackordered_until(LocalDateTime backordered_until) {
        this.backordered_until = backordered_until;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }
}
