package com.supplychain.productservice.query.controller;

import com.supplychain.productservice.query.model.CategoryResponseModel;
import com.supplychain.productservice.query.query.GetAllCategoriesQuery;
import com.supplychain.productservice.query.query.GetCategoryQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}")
public class CategoryQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("${endpoint.get-category}/{categoryId}")
    public CategoryResponseModel getCategoryDetail(@PathVariable String categoryId) {
        GetCategoryQuery getCategoryQuery = new GetCategoryQuery(categoryId);

        return queryGateway.query(getCategoryQuery, ResponseTypes.instanceOf(CategoryResponseModel.class)).join();
    }

    @GetMapping("${endpoint.get-all-categories}")
    public List<CategoryResponseModel> getAllCategories() {
        GetAllCategoriesQuery getAllCategoriesQuery = new GetAllCategoriesQuery();

        return queryGateway.query(getAllCategoriesQuery, ResponseTypes.multipleInstancesOf(CategoryResponseModel.class)).join();
    }
}
