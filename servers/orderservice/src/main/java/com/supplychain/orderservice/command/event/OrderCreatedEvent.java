package com.supplychain.orderservice.command.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCreatedEvent {
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
