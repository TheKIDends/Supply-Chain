package com.supplychain.productservice.query.projection;

import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.ProductRepository;
import com.supplychain.productservice.query.model.ProductResponseModel;
import com.supplychain.productservice.query.query.GetProductQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductProjection {
    @Autowired
    private ProductRepository productRepository;

    @QueryHandler
    public ProductResponseModel handle(GetProductQuery getProductQuery) {
        ProductResponseModel model = new ProductResponseModel();
        Product product = productRepository.getById(getProductQuery.getProductId());
        BeanUtils.copyProperties(product, model);
        return model;
    }
}
