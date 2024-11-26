package com.supplychain.orderservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderCommand {
    @TargetAggregateIdentifier
    private String requestId;

    private String senderId;
    private String recipientId;
    private String dateCreated;
    private String dateModified;
    private String requestType;
    private String requestStatus;
    private String entityName;
    private String details;


}
