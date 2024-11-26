package com.supplychain.productservice.command.controller;

import com.supplychain.productservice.command.command.CreateCategoryCommand;
import com.supplychain.productservice.command.model.CategoryRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class CategoryCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("${endpoint.add-category}")
    public String addCategory(@RequestBody CategoryRequestModel model) {
        CreateCategoryCommand command =
                new CreateCategoryCommand(UUID.randomUUID().toString(), model.getCategoryName());
        commandGateway.sendAndWait(command);
        return "addCategory";
    }
}
