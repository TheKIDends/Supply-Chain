package com.supplychain.productservice.query.projection;

import com.supplychain.productservice.command.data.Category;
import com.supplychain.productservice.command.repository.CategoryRepository;
import com.supplychain.productservice.query.model.CategoryResponseModel;
import com.supplychain.productservice.query.query.GetAllCategoriesQuery;
import com.supplychain.productservice.query.query.GetCategoryQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryProjection {
    @Autowired
    private CategoryRepository categoryRepository;

    @QueryHandler
    public CategoryResponseModel handle(GetCategoryQuery getCategoryQuery) {
        CategoryResponseModel model = new CategoryResponseModel();
        Category category = categoryRepository.getById(getCategoryQuery.getCategoryId());
        BeanUtils.copyProperties(category, model);
        return model;
    }

    @QueryHandler
    public List<CategoryResponseModel> handle(GetAllCategoriesQuery getAllCategoriesQuery) {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseModel> modelList = new ArrayList<>();

        categoryList.forEach(category -> {
            CategoryResponseModel model = new CategoryResponseModel();
            BeanUtils.copyProperties(category, model);
            modelList.add(model);
        });

        return modelList;
    }
}
