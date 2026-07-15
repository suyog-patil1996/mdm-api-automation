package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {

    /**
     * Bank Details ID
     */
    private Integer id;

    /**
     * Account Holder Name
     */
    private String accountHolderName;

    /**
     * Bank Account Number
     */
    private String accountNumber;

    /**
     * Bank Name
     */
    private String bankName;

    /**
     * Branch Name
     */
    private String branchName;

    /**
     * Savings / Current
     */
    private String accountType;

    /**
     * IFSC Code / Routing Code
     */
    private String routingCode;

    /**
     * Verification Status
     */
    @Builder.Default
    private Boolean isVerified = false;
}