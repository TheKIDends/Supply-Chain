package com.supplychain.productservice.command.aggregate;

import com.supplychain.productservice.command.command.CreateCategoryCommand;
import com.supplychain.productservice.command.event.CategoryCreatedEvent;
import com.supplychain.productservice.command.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


@Aggregate
public class CategoryAggregate {

    @AggregateIdentifier
    private String categoryId;

    private String categoryName;

    public CategoryAggregate() {
    }

    @CommandHandler
    public CategoryAggregate(CreateCategoryCommand createCategoryCommand) {
        CategoryCreatedEvent categoryCreatedEvent = new CategoryCreatedEvent();
        BeanUtils.copyProperties(createCategoryCommand, categoryCreatedEvent);
        AggregateLifecycle.apply(categoryCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.categoryId = event.getCategoryID();
        this.categoryName = event.getProductName();

    }
}
