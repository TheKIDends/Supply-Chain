package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DataType()
public class OrderItem {
    @Property()
    @JsonProperty("orderItemId")
    private String orderItemId;

    @Property()
    @JsonProperty("orderId")
    private String orderId;

    @Property()
    @JsonProperty("productId")
    private String productId;

    @Property()
    @JsonProperty("quantity")
    private String quantity;
}
