package com.exercise.faire.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eder
 */
public class InventoryPage {
    
    private List<Inventory> inventories = new ArrayList();
    
    public InventoryPage(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
