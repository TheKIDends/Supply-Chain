package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
@DataType()
public class Product {
    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("productName")
    private String productName;

    @Property()
    @JsonProperty("licenseID")
    private String licenseID;

    @Property()
    @JsonProperty("creatorId")
    private String creatorId;

    @Property()
    @JsonProperty("dateCreated")
    private String dateCreated;

    @Property()
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
