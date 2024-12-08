package org.hyperledger.fabric.supplychain.entity.request;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.supplychain.enumeration.RequestType;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DataType()
public class ProductLicense extends Request {
    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("details")
    private String details;
}
