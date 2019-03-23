package com.exercise.faire.controller;

import com.exercise.faire.model.Product;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.service.InventoryService;
import com.exercise.faire.service.ProductService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Consumes all products from a specific Brand",
    notes = "It also perform the inventory level update for each product option",
    response = ProductOption.class,
    responseContainer = "List")
    @GetMapping("/consume-all/{brandId}")
    public ResponseEntity consumeAllProductsFromBrand(@PathVariable("brandId") String brandId) {

        final List<Product> products = productService.getAllAvailableProductsByBrandId(brandId);
        
        List<ProductOption> options = new ArrayList();
        
        if(!products.isEmpty()) {
            options = inventoryService.updateIventoryByProducts(products);
        }
        
        return new ResponseEntity(options, options.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
