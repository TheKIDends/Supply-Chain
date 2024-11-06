package com.supplychain.userservice.data;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Business extends User {
    private String address;
    private String businessLicenseNumber;
    private String directorName;
    private String directorIDNumber;

}
