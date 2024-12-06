package com.supplychain.productservice.query.controller;

import com.supplychain.productservice.command.model.CategoryRequestModel;
import com.supplychain.productservice.query.model.ProductResponseModel;
import com.supplychain.productservice.query.model.ProductsByTimeRequestModel;
import com.supplychain.productservice.query.query.GetAllProductsQuery;
import com.supplychain.productservice.query.query.GetProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("${endpoint.get-all-products}")
    public List<ProductResponseModel> getAllProducts() {
        GetAllProductsQuery getAllProductsQuery = new GetAllProductsQuery();

        return queryGateway.query(getAllProductsQuery, ResponseTypes.multipleInstancesOf(ProductResponseModel.class)).join();
    }

    @GetMapping("${endpoint.get-product-by-time}")
    public List<ProductResponseModel> getProductsByTime(@RequestBody ProductsByTimeRequestModel model) {
        GetAllProductsQuery getAllProductsQuery = new GetAllProductsQuery();
        List<ProductResponseModel> responseModelList = queryGateway.query(getAllProductsQuery, ResponseTypes.multipleInstancesOf(ProductResponseModel.class)).join();

        return responseModelList;
    }
}
