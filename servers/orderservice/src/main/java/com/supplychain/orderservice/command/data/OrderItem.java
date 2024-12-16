package com.supplychain.orderservice.command.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supplychain.orderservice.enumeration.RequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @JsonProperty("OrderItemId")
    private String orderItemId;

    @Id
    @JsonProperty("productId")
    private String productId;

    @Id
    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("details")
    private String details;
}
