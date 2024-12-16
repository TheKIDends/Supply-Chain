package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DataType()
public class Product {
    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("productName")
    private String productName;

    @Property()
    @JsonProperty("licenseId")
    private String licenseId;

    @Property()
    @JsonProperty("productPrice")
    private String productPrice;

    @Property()
    @JsonProperty("categoryId")
    private String categoryId;

    @Property()
    @JsonProperty("creatorId")
    private String creatorId;

    @Property()
    @JsonProperty("dateCreated")
    private String dateCreated;

    @Property()
    @JsonProperty("productStatus")
    private String productStatus;

    @Property()
    @JsonProperty("details")
    private String details;
}
