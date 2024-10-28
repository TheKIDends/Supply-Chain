package com.supplychain.productservice.command.data;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
