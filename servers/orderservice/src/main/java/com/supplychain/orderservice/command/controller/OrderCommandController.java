package com.supplychain.orderservice.command.controller;

import com.supplychain.orderservice.command.command.CreateOrderCommand;
import com.supplychain.orderservice.command.model.OrderRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class OrderCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("${endpoint.add-order}")
    public String addOrder(@RequestBody OrderRequestModel model) {
        CreateOrderCommand command =
                new CreateOrderCommand(UUID.randomUUID().toString(), model.getSenderId(), model.getRecipientId(),
                        model.getDateCreated(), model.getDateModified(), model.getRequestType(), model.getRequestStatus(),
                        model.getEntityName(), model.getDetails());
        commandGateway.sendAndWait(command);
        return "addOrder";
    }
}
