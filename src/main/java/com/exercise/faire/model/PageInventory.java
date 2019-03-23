package com.exercise.faire.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eder
 */
public class PageInventory {
    
    private List<Inventory> inventories = new ArrayList();
    
    public PageInventory(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
