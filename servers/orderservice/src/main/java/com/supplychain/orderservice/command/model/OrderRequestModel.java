package com.supplychain.orderservice.command.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestModel {
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
