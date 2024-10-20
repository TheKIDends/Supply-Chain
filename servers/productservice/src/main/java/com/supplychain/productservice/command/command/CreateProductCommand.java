package com.supplychain.productservice.command.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;

    private String productName;
    private String licenseID;
    private String creatorId;
    private String dateCreated;
    private String details;

    public CreateProductCommand() {
    }

    public CreateProductCommand(String productId, String productName, String licenseID, String creatorId, String dateCreated, String details) {
        this.productId = productId;
        this.productName = productName;
        this.licenseID = licenseID;
        this.creatorId = creatorId;
        this.dateCreated = dateCreated;
        this.details = details;
    }
}
