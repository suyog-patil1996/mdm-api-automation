package com.oorjaa.mdm.model.vendor;

public class VendorBankRow {

    private String accountNumber;
    private String routingCode;      // IFSC
    private String accountHolderName;
    private String accountType;
    private String linkedPhoneNumber; // UPI

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLinkedPhoneNumber() {
        return linkedPhoneNumber;
    }

    public void setLinkedPhoneNumber(String linkedPhoneNumber) {
        this.linkedPhoneNumber = linkedPhoneNumber;
    }
}