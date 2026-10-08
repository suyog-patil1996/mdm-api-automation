package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.VendorConstants;
import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class VendorPayloadBuilder {

    private final VendorContext vendorContext;

    public VendorPayloadBuilder(VendorContext vendorContext) {
        this.vendorContext = vendorContext;
    }

    public CreateVendorRequest buildCreateVendorRequest() {

        String vendorName = TestDataGenerator.generateVendorName();
        String phone = TestDataGenerator.generatePhoneNumber();
        String ownerName = TestDataGenerator.generateOwnerName();
        String address1 = "Pune";
        String serviceableArea = "Across City";
        String comments = "Automation Testing";
        String registeredUnder = "PROPRIETORY";
        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();
        String upi = TestDataGenerator.generateUpiPhoneNumber();

        // -------- Context: vendor master --------
        vendorContext.setVendorName(vendorName);
        vendorContext.setPhoneNumber(phone);
        vendorContext.setOwnerName(ownerName);
        vendorContext.setAddress1(address1);
        vendorContext.setCity(VendorConstants.CITY);
        vendorContext.setState(VendorConstants.STATE);
        vendorContext.setCountry(VendorConstants.COUNTRY);
        vendorContext.setZipCode(VendorConstants.ZIP_CODE);
        vendorContext.setServiceableArea(serviceableArea);
        vendorContext.setComments(comments);
        vendorContext.setRegisteredUnder(registeredUnder);
        vendorContext.setDeliveryCenterId(VendorConstants.DEFAULT_DC_ID);

        // -------- Context: UPI (API field upiPhoneNumber) --------
        vendorContext.setUpiPhoneNumber(upi);

        DcModel dcModel = buildDefaultDc();
        VehicleModel vehicleModel = buildDefaultVehicle();
        BankDetails bankDetails = buildDefaultBankDetails(ownerName);

        return CreateVendorRequest.builder()
                .id(null)
                .nameOfCompany(vendorName)
                .vendorCode(null)
                .registeredUnder(registeredUnder)

                .address1(address1)
                .address2(null)
                .landmark(null)

                .cityId(VendorConstants.CITY_ID)
                .stateId(VendorConstants.STATE_ID)
                .countryId(VendorConstants.COUNTRY_ID)

                .city(VendorConstants.CITY)
                .state(VendorConstants.STATE)
                .country(VendorConstants.COUNTRY)

                .zipCode(VendorConstants.ZIP_CODE)

                .vendorType("TRANSPORT_PARTNER")

                .ownerName(ownerName)
                .ownerPhoneNumber(phone)
                .ownerEmailId(TestDataGenerator.generateEmail())

                .serviceableArea(serviceableArea)
                .comments(comments)

                .status("ACTIVE")

                .panCard(pan)
                .aadharNumber(aadhaar)
                .upiPhoneNumber(upi)

                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)

                .dcModelList(Collections.singletonList(dcModel))
                .vehicleList(Collections.singletonList(vehicleModel))
                .vendorGstDetails(Collections.emptyList())
                .documents(Collections.emptyList())
                .bankDetails(Collections.singletonList(bankDetails))

                .build();
    }

    public CreateVendorRequest buildDuplicateVendorRequest() {

        String duplicateVendorName = TestDataGenerator.generateVendorName();

        DcModel dcModel = buildDefaultDc();
        VehicleModel vehicleModel = buildDefaultVehicle();
        BankDetails bankDetails = buildDefaultBankDetails(
                vendorContext.getOwnerName());

        // Same phone as created vendor (do not overwrite context with new phone)
        return CreateVendorRequest.builder()
                .id(null)
                .nameOfCompany(duplicateVendorName)
                .vendorCode(null)
                .registeredUnder(vendorContext.getRegisteredUnder())

                .address1(vendorContext.getAddress1())
                .address2(null)
                .landmark(null)

                .cityId(VendorConstants.CITY_ID)
                .stateId(VendorConstants.STATE_ID)
                .countryId(VendorConstants.COUNTRY_ID)

                .city(VendorConstants.CITY)
                .state(VendorConstants.STATE)
                .country(VendorConstants.COUNTRY)

                .zipCode(VendorConstants.ZIP_CODE)

                .vendorType("TRANSPORT_PARTNER")

                .ownerName(vendorContext.getOwnerName())
                .ownerPhoneNumber(vendorContext.getPhoneNumber())
                .ownerEmailId(TestDataGenerator.generateEmail())

                .serviceableArea(vendorContext.getServiceableArea())
                .comments("Duplicate Validation")

                .status("ACTIVE")

                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)

                .dcModelList(Collections.singletonList(dcModel))
                .vehicleList(Collections.singletonList(vehicleModel))
                .vendorGstDetails(Collections.emptyList())
                .documents(Collections.emptyList())
                .bankDetails(Collections.singletonList(bankDetails))

                .build();
    }

    public ApproveVendorRequest buildApproveVendorRequest() {

        if (vendorContext.getVendorId() == null) {
            throw new IllegalStateException("Vendor Id is null. Create vendor first.");
        }

        return ApproveVendorRequest.builder()
                .id(vendorContext.getVendorId())
                .status("ACTIVE")
                .build();
    }

    public SearchVendorRequest buildSearchVendorRequest() {

        return SearchVendorRequest.builder()
                .searchKeyword(vendorContext.getVendorName())
                .page(0)
                .size(10)
                .build();
    }

    public UpdateVendorRequest buildUpdateVendorRequest() {

        String updatedVendorName = generateUpdatedVendorName();
        String updatedOwnerName = generateUpdatedOwnerName();
        String updatedPhoneNumber = TestDataGenerator.generatePhoneNumber();
        String updatedComments = generateUpdatedComments();
        String updatedAddress1 = "Pune Updated";
        String updatedServiceableArea = "Updated Service Area";
        String updatedRegisteredUnder = "PROPRIETORY";

        vendorContext.setUpdatedVendorName(updatedVendorName);
        vendorContext.setUpdatedOwnerName(updatedOwnerName);
        vendorContext.setUpdatedPhoneNumber(updatedPhoneNumber);
        vendorContext.setUpdatedComments(updatedComments);
        vendorContext.setUpdatedAddress1(updatedAddress1);
        vendorContext.setUpdatedCity(VendorConstants.CITY);
        vendorContext.setUpdatedCountry(VendorConstants.COUNTRY);
        vendorContext.setUpdatedZipCode(VendorConstants.ZIP_CODE);
        vendorContext.setUpdatedServiceableArea(updatedServiceableArea);
        vendorContext.setUpdatedRegisteredUnder(updatedRegisteredUnder);
        vendorContext.setUpdatedDeliveryCenterId(VendorConstants.DEFAULT_DC_ID);

        BankDetails bankDetails = buildDefaultBankDetails(updatedOwnerName);

        return UpdateVendorRequest.builder()
                .id(vendorContext.getVendorId())
                .nameOfCompany(updatedVendorName)
                .registeredUnder(updatedRegisteredUnder)

                .address1(updatedAddress1)
                .address2(null)
                .landmark(null)

                .cityId(VendorConstants.CITY_ID)
                .stateId(VendorConstants.STATE_ID)
                .countryId(VendorConstants.COUNTRY_ID)

                .city(VendorConstants.CITY)
                .state(VendorConstants.STATE)
                .country(VendorConstants.COUNTRY)

                .zipCode(VendorConstants.ZIP_CODE)

                .vendorType("TRANSPORT_PARTNER")

                .ownerName(updatedOwnerName)
                .ownerPhoneNumber(updatedPhoneNumber)
                .ownerEmailId(TestDataGenerator.generateEmail())

                .serviceableArea(updatedServiceableArea)
                .comments(updatedComments)

                .status("ACTIVE")

                .panCard(null)
                .upiPhoneNumber(vendorContext.getUpiPhoneNumber())
                .aadharNumber(null)

                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)

                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.emptyList())
                .documents(Collections.emptyList())
                .bankDetails(Collections.singletonList(bankDetails))

                .build();
    }

    // -------------------------------------------------------------------------

    private DcModel buildDefaultDc() {
        return DcModel.builder()
                .id(VendorConstants.DEFAULT_DC_ID)
                .build();
    }

    private VehicleModel buildDefaultVehicle() {
        return VehicleModel.builder()
                .id(VendorConstants.DEFAULT_VEHICLE_ID)
                .vehicleCount("1")
                .build();
    }

    /**
     * Builds bank details and stores values in VendorContext for DB validation.
     * Maps to: account_number, routing_code (IFSC), account_holder_name,
     * account_type, and linked_phone_number (UPI from context).
     */
    private BankDetails buildDefaultBankDetails(String accountHolderName) {

        String accountNumber = "3" + String.format("%010d",
                Math.abs(System.currentTimeMillis() % 10_000_000_000L));
        String ifsc = "SBIN0001932";
        String bankName = "State Bank of India";
        String branchName = "ADARSH COLONY";
        String accountType = "SAVINGS";

        // Store for DB asserts (latest bank row)
        vendorContext.setAccountNumber(accountNumber);
        vendorContext.setIfscCode(ifsc);
        vendorContext.setAccountHolderName(accountHolderName);
        vendorContext.setAccountType(accountType);
        vendorContext.setBankName(bankName);
        vendorContext.setBranchName(branchName);

        return BankDetails.builder()
                .id(null)
                .accountHolderName(accountHolderName)
                .accountNumber(accountNumber)
                .bankName(bankName)
                .branchName(branchName)
                .accountType(accountType)
                .routingCode(ifsc)
                .isVerified(false)
                .build();
    }

    private String generateUpdatedVendorName() {
        return "Updated_" + vendorContext.getVendorName();
    }

    private String generateUpdatedOwnerName() {
        return "Updated Owner";
    }

    private String generateUpdatedComments() {
        return "Updated By Automation";
    }
}