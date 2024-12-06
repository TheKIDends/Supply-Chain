package com.supplychain.productservice.query.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByTimeRequestModel {
    private String startTime;
    private String endTime;
}
