package com.supplychain.commonservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CarrierResponseCommonModel extends UserResponseCommonModel {
    private String address;
    private String businessLicenseNumber;
    private String managerName;
}