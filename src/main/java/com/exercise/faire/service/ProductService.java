/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exercise.faire.service;

import com.exercise.faire.model.Order;
import com.exercise.faire.model.Product;
import com.exercise.faire.model.ProductOption;
import java.util.List;

/**
 *
 * @author eder
 */
public interface ProductService {
    
    public List<Product> getAllAvailableProductsByBrandId(String brandId);
    
    public void updateAvailableQuantityOfProductOptions(List<Product> product);

    public List<ProductOption> getProductOptionsByOrders(List<Order> orders);
    
}
