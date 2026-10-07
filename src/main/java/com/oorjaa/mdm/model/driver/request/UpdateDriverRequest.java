package com.oorjaa.mdm.model.driver.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateDriverRequest {

    private Integer id;
    private Integer vendorId;
    private Integer vehicleId;
    private Integer deliveryCenterId;
    private String drivingLicense;
    private String dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private List<Object> vehicleClasses;
    private String countryId;
    private String stateId;
    private String cityId;
    private String country;
    private String state;
    private String city;
    private String address1;
    private String address2;
    private String address3;
    private Boolean isLicenceVerified;
    private List<DriverDocument> documents;
    private String vehicleRegistrationNumber;
    private Boolean isVehicleAssigned;
    private Integer permanentDeliveryCenterId;
    private Boolean dcAssigned;
}