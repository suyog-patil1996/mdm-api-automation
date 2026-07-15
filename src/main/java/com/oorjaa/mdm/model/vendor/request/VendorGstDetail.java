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

    /**
     * GST Detail ID
     */
    private Integer id;

    /**
     * GST Number
     */
    private String gstNumber;

    /**
     * State ID
     */
    private String stateId;

    /**
     * State Name
     */
    private String state;

    /**
     * Address associated with GST
     */
    private String address;

    /**
     * Indicates whether this GST is the primary GST.
     */
    @Builder.Default
    private Boolean primary = false;

    /**
     * Verification status.
     */
    @Builder.Default
    private Boolean verified = false;
}