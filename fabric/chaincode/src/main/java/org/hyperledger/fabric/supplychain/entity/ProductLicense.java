package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.supplychain.enumeration.RequestType;

@Getter
@Setter
@ToString
@DataType()
public class ProductLicense extends Request {
    @Property()
    @JsonProperty("companyId")
    private String companyId;

    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("details")
    private String details;

    public ProductLicense() {
        this.requestType = RequestType.PRODUCT_LICENSE;
        this.entityName = ProductLicense.class.getSimpleName();
    }

    @Builder
    public ProductLicense(String requestId, String senderId, String recipientId, String dateCreated, String dateModified, String requestType, String requestStatus, String companyId, String productId, String details) {
        super(requestId, senderId, recipientId, dateCreated, dateModified, requestType, requestStatus, ProductLicense.class.getSimpleName());
        this.companyId = companyId;
        this.productId = productId;
        this.details = details;
    }
}
