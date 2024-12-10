package com.supplychain.productservice.command.event;

import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;
import com.supplychain.productservice.command.data.Category;
import com.supplychain.productservice.command.repository.CategoryRepository;
import com.supplychain.userservice.enumeration.UserRole;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryEventsHandler {

    @Autowired
    private CategoryRepository categoryRepository;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        Category category = new Category();
        BeanUtils.copyProperties(event, category);




//        categoryRepository.save(category);
    }
}
