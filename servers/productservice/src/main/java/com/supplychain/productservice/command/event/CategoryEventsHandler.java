package com.supplychain.productservice.command.event;

import com.owlike.genson.Genson;
import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;
import com.supplychain.productservice.command.data.Category;
import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.CategoryRepository;
import com.supplychain.productservice.service.HyperledgerService;
import com.supplychain.userservice.data.Admin;
import com.supplychain.userservice.data.Business;
import com.supplychain.userservice.data.User;
import com.supplychain.userservice.enumeration.UserRole;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CategoryEventsHandler {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private HyperledgerService hyperledgerService;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        Category category = new Category();
        BeanUtils.copyProperties(event, category);

        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (existingCategory == null) {
            categoryRepository.save(category);
        } else {
            System.out.println("Category with the same name already exists.");
        }

//        GetDetailsUserQuery getDetailsUserQuery  = new GetDetailsUserQuery(event.getCreatorId(), null);
//
//        UserResponseCommonModel userModel =
//                queryGateway.query(getDetailsUserQuery, ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();
//
//        Admin admin = new Admin();
//        BeanUtils.copyProperties(userModel, admin);
//
//        Genson genson = new Genson();
//        String categoryStr = genson.serialize(category);
//        JSONObject categoryJSONObject = new JSONObject(categoryStr);
//
//        try {
//            Category addedCategory = hyperledgerService.addCategory(
//                    admin,
//                    categoryJSONObject
//            );
//            System.out.println("category: " + addedCategory);
//
//        } catch (Exception exception) {
//            System.out.println(exception);
//        }

    }
}
