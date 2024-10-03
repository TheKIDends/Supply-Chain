package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
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
    @JsonProperty("status")
    private String status;

    @Property()
    @JsonProperty("details")
    private String details;

    public ProductItem() {
    }

    @Builder
    public ProductItem(String productItemId, String productId, String productItemType, String containerId,
                       String productionDate, String expirationDate, String creatorId, String ownerId, String status,
                       String details
    ) {
        this.productItemId = productItemId;
        this.productId = productId;
        this.productItemType = productItemType;
        this.containerId = containerId;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.status = status;
        this.details = details;
    }
}
