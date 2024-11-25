package com.supplychain.orderservice.command.data;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "orders")
public class Order {
    @Id
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("ordererId")
    protected String ordererId;

    @JsonProperty("dateCreated")
    protected String dateCreated;

    @JsonProperty("dateModified")
    protected String dateModified;

    @JsonProperty("details")
    private String details;
}
