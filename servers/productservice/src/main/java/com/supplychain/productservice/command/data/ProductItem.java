package com.supplychain.productservice.command.data;

import com.owlike.genson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productItem")
public class ProductItem {
    @Id
    @JsonProperty("productItemId")
    private String productItemId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("productItemType")
    private String productItemType;

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
