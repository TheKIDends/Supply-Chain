package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
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

     public OrderItem() {
    }

    @Builder
    public OrderItem(String orderItemId, String orderId, String productId, String quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
