package com.blockchain.supplychain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Business extends User {
    private String address;
    private String phoneNumber;
    private String businessLicenseNumber;
    private String directorName;
    private String directorIDNumber;

    public String getAddress() {
        return address;
    }

    public Business setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Business setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public Business setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
        return this;
    }

    public String getDirectorName() {
        return directorName;
    }

    public Business setDirectorName(String directorName) {
        this.directorName = directorName;
        return this;
    }

    public String getDirectorIDNumber() {
        return directorIDNumber;
    }

    public Business setDirectorIDNumber(String directorIDNumber) {
        this.directorIDNumber = directorIDNumber;
        return this;
    }
}
