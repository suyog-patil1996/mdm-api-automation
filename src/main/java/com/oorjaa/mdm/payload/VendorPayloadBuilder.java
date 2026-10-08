package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.VendorConstants;
import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class VendorPayloadBuilder {

    private final VendorContext vendorContext;
    private final Random random = new Random();

    public VendorPayloadBuilder(VendorContext vendorContext) {
        this.vendorContext = vendorContext;
    }

    public CreateVendorRequest buildCreateVendorRequest() {

        String vendorName = TestDataGenerator.generateVendorName();
        String phone = TestDataGenerator.generatePhoneNumber(); // 10 digits, no +91
        String ownerName = TestDataGenerator.generateOwnerName();
        String address1 = "Pune";
        String serviceableArea = "Across City";
        String comments = "Automation Testing";
        String registeredUnder = "PROPRIETORY";
        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();
        String upi = TestDataGenerator.generateUpiPhoneNumber();
        String msme = "UDYAM-MH-" + (10000 + random.nextInt(90000));
        String gst = "22AAAAA" + String.format("%04d", random.nextInt(10000)) + "A1Z3";

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
        vendorContext.setUpiPhoneNumber(upi);

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
                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.singletonList(buildGstDetail(gst)))
                .documents(buildVendorDocuments(pan, aadhaar, msme, gst))
                .bankDetails(Collections.singletonList(bankDetails))
                .build();
    }

    public CreateVendorRequest buildDuplicateVendorRequest() {

        String duplicateVendorName = TestDataGenerator.generateVendorName();
        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();
        String msme = "UDYAM-MH-" + (10000 + random.nextInt(90000));
        String gst = "22AAAAA" + String.format("%04d", random.nextInt(10000)) + "A1Z3";

        BankDetails bankDetails = buildDefaultBankDetails(vendorContext.getOwnerName());

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
                .panCard(pan)
                .aadharNumber(aadhaar)
                .upiPhoneNumber(vendorContext.getUpiPhoneNumber())
                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)
                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.singletonList(buildGstDetail(gst)))
                .documents(buildVendorDocuments(pan, aadhaar, msme, gst))
                .bankDetails(Collections.singletonList(bankDetails))
                .build();
    }

    public ApproveVendorRequest buildApproveVendorRequest() {

        if (vendorContext.getVendorId() == null) {
            throw new IllegalStateException("Vendor Id is null. Create vendor first.");
        }

        String phone = vendorContext.getPhoneNumber();
        if (phone != null && !phone.startsWith("+91")) {
            phone = "+91" + phone;
        }

        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();
        String msme = "UDYAM-MH-" + (10000 + new java.util.Random().nextInt(90000));
        String gst = "22AAAAA" + String.format("%04d", new java.util.Random().nextInt(10000)) + "A1Z3";

        BankDetails bankDetails = buildDefaultBankDetails(
                vendorContext.getOwnerName() != null
                        ? vendorContext.getOwnerName()
                        : "AUTO HOLDER");

        return ApproveVendorRequest.builder()
                .id(vendorContext.getVendorId())
                .nameOfCompany(vendorContext.getVendorName())
                .registeredUnder(vendorContext.getRegisteredUnder())
                .address1(vendorContext.getAddress1())
                .address2(null)
                .landmark(null)
                .cityId(VendorConstants.CITY_ID)
                .stateId(VendorConstants.STATE_ID)
                .countryId(VendorConstants.COUNTRY_ID)
                .country(VendorConstants.COUNTRY)
                .state(VendorConstants.STATE)
                .city(VendorConstants.CITY)
                .zipCode(VendorConstants.ZIP_CODE)
                .vendorType("TRANSPORT_PARTNER")
                .ownerName(vendorContext.getOwnerName())
                .ownerPhoneNumber(phone)
                .ownerEmailId(TestDataGenerator.generateEmail())
                .serviceableArea(vendorContext.getServiceableArea())
                .comments(vendorContext.getComments())
                .status("ACTIVE")
                .upiPhoneNumber(vendorContext.getUpiPhoneNumber())
                .panCard(pan)
                .aadharNumber(aadhaar)
                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)
                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.singletonList(buildGstDetail(gst)))
                .documents(buildVendorDocuments(pan, aadhaar, msme, gst))
                .bankDetails(Collections.singletonList(bankDetails))
                .requestType("APPROVAL_PAGE")
                .build();
    }

    public SearchVendorRequest buildSearchVendorRequest() {
        return SearchVendorRequest.builder()
                .searchText(vendorContext.getPhoneNumber())
                .pageId(0)
                .limit(10)
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

        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();
        String msme = "UDYAM-MH-" + (10000 + random.nextInt(90000));
        String gst = "22AAAAA" + String.format("%04d", random.nextInt(10000)) + "A1Z3";

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
                .panCard(pan)
                .upiPhoneNumber(vendorContext.getUpiPhoneNumber())
                .aadharNumber(aadhaar)
                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)
                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.singletonList(buildGstDetail(gst)))
                .documents(buildVendorDocuments(pan, aadhaar, msme, gst))
                .bankDetails(Collections.singletonList(bankDetails))
                .build();
    }

    // -------------------------------------------------------------------------

    private List<Document> buildVendorDocuments(
            String pan,
            String aadhaar,
            String msme,
            String gst) {

        return Arrays.asList(
                Document.builder()
                        .documentCategory("BUSINESS_REG")
                        .documentNumber(msme)
                        .countryCode("IN")
                        .documentName("msmeNumber")
                        .build(),
                Document.builder()
                        .documentCategory("PERSONAL_TAX")
                        .documentNumber(pan)
                        .countryCode("IN")
                        .documentName("panNumber")
                        .build(),
                Document.builder()
                        .documentCategory("CANCELLED_CHEQUE")
                        .documentNumber(null)
                        .countryCode("IN")
                        .documentName("cancelledCheque")
                        .build(),
                Document.builder()
                        .documentCategory("TAX_ID")
                        .documentNumber(gst)
                        .countryCode("IN")
                        .documentName("gstNumber")
                        .build(),
                Document.builder()
                        .documentCategory("NATIONAL_ID")
                        .documentNumber(aadhaar)
                        .countryCode("IN")
                        .documentName("aadharCardNumber")
                        .build()
        );
    }

    private VendorGstDetail buildGstDetail(String gstNumber) {
        return VendorGstDetail.builder()
                .gstNumber(gstNumber)
                .isGstInVerified(false)
                .state("Assam")
                .stateId("9c8571d2-3c25-11ee-bd0f-0a9d1c57a228")
                .gstPercentage(5.0)
                .isReverseChargeApplicable(false)
                .gstInDetails(null)
                .build();
    }

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

    private BankDetails buildDefaultBankDetails(String accountHolderName) {

        String accountNumber = "3" + String.format("%010d",
                Math.abs(System.currentTimeMillis() % 10_000_000_000L));
        String ifsc = "SBIN0001932";
        String bankName = "State Bank of India";
        String branchName = "ADARSH COLONY";
        String accountType = "SAVINGS";

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