package com.supplychain.productservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;

    private String productName;
    private String licenseID;
    private String creatorId;
    private String dateCreated;
    private String details;


}