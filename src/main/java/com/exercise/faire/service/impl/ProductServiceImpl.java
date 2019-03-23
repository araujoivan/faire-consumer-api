package com.exercise.faire.service.impl;

import com.exercise.faire.model.Order;
import com.exercise.faire.model.Product;
import com.exercise.faire.model.ProductOption;
import com.exercise.faire.model.ProductPage;
import com.exercise.faire.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    @Override
    public List<Product> getAllAvailableProductsByBrandId(String brandId) {

        final List<Product> products = getAllProducts();

        products.forEach(product -> {
            product.getOptions().removeIf(option -> !option.isActive() || option.getAvailable_quantity() == 0);
        });

        products.removeIf(product -> !product.isActive()
                || product.getOptions().isEmpty()
                || !product.getBrand_id().equalsIgnoreCase(brandId));

        return products;
    }
    
    // TODO: Create a method with a delimiter. 
    private List<Product> getAllProducts() {

        final String queryUrl = baseUrl.concat("products?limit=250&page=");

        final List<Product> products = new ArrayList();

        int pageNumber = 1;

        boolean hasResults = true;

        do {

            // TODO: verify the reason why we don't have a filter by brand?
            final ResponseEntity<ProductPage> response
                    = restTemplate.getForEntity(queryUrl.concat(String.valueOf(pageNumber++)), ProductPage.class);

            hasResults = nonNull(response.getBody())
                    && nonNull(response.getBody().getProducts())
                    && !response.getBody().getProducts().isEmpty();

            if (hasResults) {
                products.addAll(response.getBody().getProducts());
            }

        } while (hasResults);
        
        return products;
    }

    @Override
    public void updateAvailableQuantityOfProductOptions(List<Product> products) {

        final String queryUrl = baseUrl.concat("products/options/");

        products.forEach(product -> {

            product.getOptions().forEach(option -> {

                int quantity = option.getAvailable_quantity();

                option.setAvailable_quantity(0);

                restTemplate.patchForObject(queryUrl.concat(option.getId()), option, ProductOption.class);
            });
        });
    }
  
    @Override
    public List<ProductOption> getProductOptionsByOrders(List<Order> orders) {

        final List<ProductOption> options = new ArrayList();
        
        final List<String> productOptionIds = getProductOptionIds(orders);
        
        final List<Product> products = getAllProducts();
        
        products.forEach(product -> {
            
            product.getOptions().forEach(productOption -> {
               
                if(!productOptionIds.contains(productOption.getId())) {
                   options.add(productOption);
                }
            });
        });
        
        return options;

    }

    private List<String> getProductOptionIds(List<Order> orders) {

        final List<String> productOptionIds = new ArrayList();

        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                if(!productOptionIds.contains(item.getProduct_option_id())) {
                   productOptionIds.add(item.getProduct_id());
                }
            });
        });

        return productOptionIds;
    }
}
