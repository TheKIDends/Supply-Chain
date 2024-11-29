package com.supplychain.productservice.command.data.request;

import com.supplychain.productservice.enumeration.RequestType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLicense extends Request {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("details")
    private String details;
}
