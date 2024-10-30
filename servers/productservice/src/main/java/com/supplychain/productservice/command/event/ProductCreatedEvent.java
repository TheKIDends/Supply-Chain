package com.supplychain.productservice.command.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCreatedEvent {
    private String productId;
    private String productName;
    private String licenseID;
    private String creatorId;
    private String dateCreated;
    private String details;
}
