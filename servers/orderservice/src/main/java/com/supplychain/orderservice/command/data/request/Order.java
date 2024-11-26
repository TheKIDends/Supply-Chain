package com.supplychain.orderservice.command.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supplychain.orderservice.enumeration.RequestType;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
public class Order extends Request {
    @JsonProperty("details")
    private String details;

    public Order() {
        super();
        this.requestType = RequestType.ORDER;
        this.entityName = Order.class.getSimpleName();
    }

}
