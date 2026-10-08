package com.oorjaa.mdm.model.da.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateDaRequest {

    private Integer id;
    private String otp;
    private String firstName;
    private Integer vendorId;
    private Boolean smartPhone;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String educationQualification;
    private String address1;
    private String address2;
    private String address3;
    private String countryId;
    private String stateId;
    private String cityId;
    private String country;
    private String state;
    private String city;
    private Boolean policeVerification;
    private Boolean isAadharVerified;
    private Boolean isVerified;
    private List<DaDocument> documents;
}