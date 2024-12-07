package com.supplychain.productservice.command.event;


import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;

import com.supplychain.userservice.data.Business;
import com.supplychain.userservice.enumeration.UserRole;

import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.ProductRepository;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductEventsHandler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private transient QueryGateway queryGateway;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);


        GetDetailsUserQuery getDetailsUserQuery  = new GetDetailsUserQuery(event.getCreatorId());


        UserResponseCommonModel model =
                queryGateway.query(getDetailsUserQuery, ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();

        
        if (Objects.equals(model.getRole(), UserRole.BUSINESS)) {
            Business business = new Business();
            BeanUtils.copyProperties(model, business);
            System.out.println("hah");
            System.out.println(business.getDirectorIDNumber());
        }
         

        productRepository.save(product);
    }
}
