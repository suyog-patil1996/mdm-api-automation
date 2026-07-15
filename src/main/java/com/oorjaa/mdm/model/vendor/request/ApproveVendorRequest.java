package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApproveVendorRequest extends UpdateVendorRequest {

    /**
     * API request type.
     * Example: APPROVAL_PAGE
     */
    private String requestType;
}