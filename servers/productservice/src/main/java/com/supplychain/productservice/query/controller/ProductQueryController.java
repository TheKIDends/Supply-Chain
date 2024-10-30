package com.supplychain.productservice.query.controller;

import com.supplychain.productservice.query.model.ProductResponseModel;
import com.supplychain.productservice.query.query.GetProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}")
public class ProductQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("${endpoint.get-product}/{productId}")
    public ProductResponseModel getProductDetail(@PathVariable String productId) {
        GetProductQuery getProductQuery = new GetProductQuery(productId);

        return queryGateway.query(getProductQuery, ResponseTypes.instanceOf(ProductResponseModel.class)).join();
    }
}
