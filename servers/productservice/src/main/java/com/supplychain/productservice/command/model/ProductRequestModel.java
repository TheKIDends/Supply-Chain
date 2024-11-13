package com.supplychain.productservice.command.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestModel {
    private String productName;
    private String productPrice;
    private String categoryID;
    private String creatorId;
    private String dateCreated;
    private String details;
}
