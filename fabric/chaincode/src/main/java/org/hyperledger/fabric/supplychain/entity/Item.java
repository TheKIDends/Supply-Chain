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
public class Item {
    @Property()
    @JsonProperty("itemId")
    private String itemId;

    @Property()
    @JsonProperty("productId")
    private String productId;

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

    public Item() {
    }

    @Builder
    public Item(String itemId, String productId, String productionDate, String expirationDate,
                String creatorId, String ownerId, String itemStatus, String details
    ) {
        this.itemId = itemId;
        this.productId = productId;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.itemStatus = itemStatus;
        this.details = details;
    }
}
