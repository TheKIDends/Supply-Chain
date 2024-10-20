package com.supplychain.productservice.command.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supplychain.productservice.enumeration.RequestType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductLicense extends Request {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("details")
    private String details;

    public ProductLicense() {
        super();
        this.requestType = RequestType.PRODUCT_LICENSE;
        this.entityName = ProductLicense.class.getSimpleName();
    }

    @Builder
    public ProductLicense(String requestId, String senderId, String recipientId, String dateCreated, String dateModified, String requestType, String requestStatus, String productId, String details) {
        super(requestId, senderId, recipientId, dateCreated, dateModified, requestType, requestStatus, ProductLicense.class.getSimpleName());
        this.productId = productId;
        this.details = details;
    }
}
