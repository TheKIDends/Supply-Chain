package com.blockchain.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("itemType")
    private String itemType;

    @JsonProperty("containerId")
    private String containerId;

    @JsonProperty("productionDate")
    private String productionDate;

    @JsonProperty("expirationDate")
    private String expirationDate;

    @JsonProperty("creatorId")
    private String creatorId;

    @JsonProperty("ownerId")
    private String ownerId;

    @JsonProperty("itemStatus")
    private String itemStatus;

    @JsonProperty("details")
    private String details;

    public Item() {
    }

    @Builder
    public Item(String itemId, String productId, String itemType, String containerId, String productionDate,
                String expirationDate, String creatorId, String ownerId, String itemStatus, String details
    ) {
        this.itemId = itemId;
        this.productId = productId;
        this.itemType = itemType;
        this.containerId = containerId;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.itemStatus = itemStatus;
        this.details = details;
    }
}
