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
public class ProductItem {
    @Property()
    @JsonProperty("productItemId")
    private String productItemId;

    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("productItemType")
    private String productItemType;

    @Property()
    @JsonProperty("containerId")
    private String containerId;

    @Property()
    @JsonProperty("productionDate")
    private String productionDate;

    @Property()
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Property()
    @JsonProperty("creatorId")
    private String creatorId;

    @Property()
    @JsonProperty("ownerId")
    private String ownerId;

    @Property()
    @JsonProperty("itemStatus")
    private String itemStatus;

    @Property()
    @JsonProperty("details")
    private String details;

}
