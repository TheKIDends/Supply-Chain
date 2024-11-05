package com.blockchain.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItem {
    @JsonProperty("orderItemId")
    private String orderItemId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("productId")
    private String productId;

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
