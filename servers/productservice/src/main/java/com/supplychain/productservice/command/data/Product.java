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
@Table(name = "product")
public class Product {
    @Id
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productPrice")
    private String productPrice;

    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("licenseId")
    private String licenseId;

    @JsonProperty("creatorId")
    private String creatorId;

    @JsonProperty("dateCreated")
    private String dateCreated;

    @JsonProperty("productStatus")
    private String productStatus;

    @JsonProperty("details")
    private String details;
}
