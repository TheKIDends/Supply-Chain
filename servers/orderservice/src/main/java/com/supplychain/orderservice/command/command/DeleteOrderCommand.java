package com.supplychain.orderservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderCommand {
    @TargetAggregateIdentifier
    private String requestId;

}
