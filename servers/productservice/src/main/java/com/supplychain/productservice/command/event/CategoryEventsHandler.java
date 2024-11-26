package com.supplychain.productservice.command.event;

import com.supplychain.productservice.command.data.Category;
import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.CategoryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryEventsHandler {

    @Autowired
    private CategoryRepository categoryRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Category category = new Category();
        BeanUtils.copyProperties(event, category);
        categoryRepository.save(category);
    }
}
