package com.supplychain.productservice.command.data;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
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
}
