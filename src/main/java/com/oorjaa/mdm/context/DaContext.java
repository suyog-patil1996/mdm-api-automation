package com.oorjaa.mdm.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DaContext {

    private Integer daId;
    private Integer userId;
    private Integer vendorId;

    private String firstName;
    private String phoneNumber;
    private String educationQualification;
    private String address1;
    private String city;
    private String state;
    private String country;

    private String updatedFirstName;
    private String updatedEducationQualification;
    private String updatedAddress1;
    private String updatedCity;
    private String updatedPhoneNumber;
}