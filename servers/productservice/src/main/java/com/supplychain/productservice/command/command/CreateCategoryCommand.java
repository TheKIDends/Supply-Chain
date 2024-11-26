package com.supplychain.productservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryCommand {

    @TargetAggregateIdentifier
    private String categoryId;

    private String categoryName;
    private String categoryStatus;
}
