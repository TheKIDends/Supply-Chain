package com.supplychain.productservice.query.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel {
    private String productId;
    private String productName;
    private String productPrice;
    private String categoryId;
    private String creatorId;
    private String dateCreated;
    private String productStatus;
    private String details;
}
