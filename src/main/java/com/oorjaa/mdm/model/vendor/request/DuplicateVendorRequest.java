package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateVendorRequest {

    /**
     * Existing Vendor ID that needs to be duplicated.
     */
    private Integer vendorId;

    /**
     * New Vendor Code for the duplicated vendor.
     */
    private String vendorCode;

    /**
     * New Company Name (Optional).
     */
    private String nameOfCompany;

    /**
     * Copy all documents from source vendor.
     */
    @Builder.Default
    private Boolean copyDocuments = true;

    /**
     * Copy bank details from source vendor.
     */
    @Builder.Default
    private Boolean copyBankDetails = true;

    /**
     * Copy GST details from source vendor.
     */
    @Builder.Default
    private Boolean copyGstDetails = true;
}