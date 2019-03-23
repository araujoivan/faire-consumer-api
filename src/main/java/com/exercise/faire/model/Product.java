package com.exercise.faire.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    
    private String id;
    private String brand_id;
    private boolean active;
    private List<ProductOption> options = new ArrayList();
    private int unit_multiplier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public int getUnit_multiplier() {
        return unit_multiplier;
    }

    public void setUnit_multiplier(int unit_multiplier) {
        this.unit_multiplier = unit_multiplier;
    }
}
