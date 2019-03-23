package com.exercise.faire.model;

/**
 *
 * @author eder
 */
public class OrderItem {
    
    private String id;
    private String sku;
    private String product_id;
    private String product_option_id;
    private String order_id;
    private int quantity;
    private int price_cents;

    public int getPrice_cents() {
        return price_cents;
    }

    public void setPrice_cents(int price_cents) {
        this.price_cents = price_cents;
    }
    
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getProduct_option_id() {
        return product_option_id;
    }

    public void setProduct_option_id(String product_option_id) {
        this.product_option_id = product_option_id;
    }
}
