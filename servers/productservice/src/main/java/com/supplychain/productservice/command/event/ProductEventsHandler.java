package com.supplychain.productservice.command.event;


import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;

import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.ProductRepository;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

      
        UserResponseCommonModel userResponseCommonModel =
                queryGateway.query(getDetailsUserQuery, ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();

        System.out.println(userResponseCommonModel);




        productRepository.save(product);
    }
}
