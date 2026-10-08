package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorGstDetail {

    private Integer id;
    private String gstNumber;
    private String stateId;
    private String state;
    private String address;

    private Boolean isGstInVerified;
    private Double gstPercentage;
    private Boolean isReverseChargeApplicable;
    private Object gstInDetails;

    @Builder.Default
    private Boolean primary = false;

    @Builder.Default
    private Boolean verified = false;
}