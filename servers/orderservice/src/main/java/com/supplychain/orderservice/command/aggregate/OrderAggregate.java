package com.supplychain.orderservice.command.aggregate;

import com.supplychain.orderservice.command.command.CreateOrderCommand;
import com.supplychain.orderservice.command.event.OrderCreatedEvent;
import com.supplychain.productservice.command.command.CreateProductCommand;
import com.supplychain.productservice.command.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String requestId;

    private String senderId;
    private String recipientId;
    private String dateCreated;
    private String dateModified;
    private String requestType;
    private String requestStatus;
    private String entityName;
    private String details;
    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createProductCommand) {
        CreateOrderCommand orderCreatedEvent = new CreateOrderCommand();
        BeanUtils.copyProperties(createProductCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.requestId = event.getRequestId();
        this.senderId = event.getSenderId();
        this.recipientId = event.getRecipientId();
        this.dateCreated = event.getDateCreated();
        this.dateModified = event.getDateModified();
        this.requestType = event.getRequestType();
        this.requestStatus = event.getRequestStatus();
        this.entityName = event.getEntityName();
        this.details = event.getDetails();
    }
}
