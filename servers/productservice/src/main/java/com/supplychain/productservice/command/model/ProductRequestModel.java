package com.supplychain.productservice.command.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequestModel {

    private String productId;
    private String productName;
    private String licenseID;
    private String creatorId;
    private String dateCreated;
    private String details;

    public ProductRequestModel() {
    }

    public ProductRequestModel(String productId, String productName, String licenseID, String creatorId, String dateCreated, String details) {
        this.productId = productId;
        this.productName = productName;
        this.licenseID = licenseID;
        this.creatorId = creatorId;
        this.dateCreated = dateCreated;
        this.details = details;
    }
}
