package com.supplychain.productservice.command.controller;

import com.supplychain.productservice.command.command.CreateProductCommand;
import com.supplychain.productservice.command.model.ProductRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class ProductCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("${endpoint.add-product}")
    public String addProduct(@RequestBody ProductRequestModel model) {
        CreateProductCommand command =
                new CreateProductCommand(UUID.randomUUID().toString(), model.getProductName(), model.getProductPrice(),
                        model.getCategoryID(), null, model.getCreatorId(), model.getDateCreated(), model.getDetails());
        commandGateway.sendAndWait(command);
        return "addProduct";
    }
}
