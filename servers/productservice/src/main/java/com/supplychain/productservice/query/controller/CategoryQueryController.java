package com.supplychain.productservice.query.controller;

import com.supplychain.productservice.command.data.Category;
import com.supplychain.productservice.command.repository.CategoryRepository;
import com.supplychain.productservice.query.model.CategoryResponseModel;
import com.supplychain.productservice.query.query.GetAllCategoriesQuery;
import com.supplychain.productservice.query.query.GetCategoryQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.base-path}")
public class CategoryQueryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QueryGateway queryGateway;

//    @GetMapping("${endpoint.get-category}/{categoryId}")
//    public CategoryResponseModel getCategoryDetail(@PathVariable String categoryId) {
//        GetCategoryQuery getCategoryQuery = new GetCategoryQuery(categoryId);
//
//        return queryGateway.query(getCategoryQuery, ResponseTypes.instanceOf(CategoryResponseModel.class)).join();
//    }
    @GetMapping("${endpoint.get-category}/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable String categoryId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Category categories = categoryRepository.findByCategoryId(categoryId);

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", categories);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Lỗi khi lấy dữ liệu");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("${endpoint.get-all-categories}")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Category> categories = categoryRepository.findAll();

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", categories);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Lỗi khi lấy dữ liệu");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
