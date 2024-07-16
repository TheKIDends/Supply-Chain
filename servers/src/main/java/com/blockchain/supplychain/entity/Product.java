package com.blockchain.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
public class Product {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("licenseID")
    private String licenseID;

    @JsonProperty("creatorId")
    private String creatorId;

    @JsonProperty("dateCreated")
    private String dateCreated;

    @JsonProperty("details")
    private String details;

    public Product() {
    }

    @Builder
    public Product(String productId, String productName, String licenseID, String creatorId, String dateCreated, String details) {
        this.productId = productId;
        this.productName = productName;
        this.licenseID = licenseID;
        this.creatorId = creatorId;
        this.dateCreated = dateCreated;
        this.details = details;
    }
}
