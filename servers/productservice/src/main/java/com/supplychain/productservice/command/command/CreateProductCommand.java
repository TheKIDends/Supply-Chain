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
    private String productPrice;
    private String categoryId;
    private String licenseId;
    private String creatorId;
    private String dateCreated;
    private String productStatus;
    private String details;
}
