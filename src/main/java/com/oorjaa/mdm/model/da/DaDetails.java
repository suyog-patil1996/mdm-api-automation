package com.oorjaa.mdm.model.da;

import lombok.Data;

@Data
public class DaDetails {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String keycloakUserId;
    private String keycloakUsername;
    private Integer daId;
    private String state;
    private String city;
    private Integer createdBy;
    private String createdDate;
    private Integer updatedBy;
    private String updatedDate;
    private String address1;
    private Integer vendorId;
}