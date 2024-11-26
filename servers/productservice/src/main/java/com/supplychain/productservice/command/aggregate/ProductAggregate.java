package com.supplychain.productservice.command.aggregate;

import com.supplychain.productservice.command.command.CreateProductCommand;
import com.supplychain.productservice.command.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;

    private String productName;
    private String productPrice;
    private String categoryID;
    private String licenseID;
    private String creatorId;
    private String dateCreated;
    private String details;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.productName = event.getProductName();
        this.productPrice = event.getProductPrice();
        this.categoryID = event.getCategoryID();
        this.licenseID = event.getLicenseID();
        this.creatorId = event.getCreatorId();
        this.dateCreated = event.getDateCreated();
        this.details = event.getDetails();
    }
}
