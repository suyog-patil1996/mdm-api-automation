package com.oorjaa.mdm.model.driver.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDocument {

    private String documentCategory;
    private String documentNumber;
    private String countryCode;
    private String documentName;
    private String expiryDate;
    private String issueDate;
    private String verificationStatus;
}