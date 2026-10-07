package com.oorjaa.mdm.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DriverContext {

    private Integer driverId;
    private Integer userId;
    private Integer vendorId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String drivingLicense;

    private String address1;
    private String city;
    private String state;
    private String country;

    // After update
    private String updatedFirstName;
    private String updatedAddress1;
    private String updatedCity;
    private String updatedPhoneNumber;
}