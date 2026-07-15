package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.VendorConstants;
import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class VendorPayloadBuilder {

    private final VendorContext vendorContext;

    public VendorPayloadBuilder(VendorContext vendorContext) {
        this.vendorContext = vendorContext;
    }

    public CreateVendorRequest buildCreateVendorRequest() {

        String vendorName = TestDataGenerator.generateVendorName();
        String phone = TestDataGenerator.generatePhoneNumber();

        vendorContext.setVendorName(vendorName);
        vendorContext.setPhoneNumber(phone);
        vendorContext.setOwnerName(TestDataGenerator.generateOwnerName());

        vendorContext.setAddress1("Pune");
        vendorContext.setCity(VendorConstants.CITY);
        vendorContext.setCountry(VendorConstants.COUNTRY);
        vendorContext.setZipCode(VendorConstants.ZIP_CODE);

        vendorContext.setServiceableArea("Across City");
        vendorContext.setComments("Automation Testing");
        vendorContext.setRegisteredUnder("PROPRIETORY");

        vendorContext.setDeliveryCenterId(VendorConstants.DEFAULT_DC_ID);

        DcModel dcModel = buildDefaultDc();

        VehicleModel vehicleModel = buildDefaultVehicle();

        BankDetails bankDetails = buildDefaultBankDetails();

        return CreateVendorRequest.builder()
                .id(null)
                .nameOfCompany(vendorName)
                .vendorCode(null)
                .registeredUnder(vendorContext.getRegisteredUnder())

                .address1("Pune")
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
                .ownerPhoneNumber(phone)
                .ownerEmailId(TestDataGenerator.generateEmail())

                .serviceableArea(vendorContext.getServiceableArea())
                .comments(vendorContext.getComments())

                .status("ACTIVE")

                .panCard(TestDataGenerator.generatePanNumber())
                .aadharNumber(TestDataGenerator.generateAadharNumber())
                .upiPhoneNumber(TestDataGenerator.generateUpiPhoneNumber())

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

        String duplicateVendorName =
                TestDataGenerator.generateVendorName();

        DcModel dcModel = buildDefaultDc();

        VehicleModel vehicleModel = buildDefaultVehicle();

        BankDetails bankDetails = buildDefaultBankDetails();

        return CreateVendorRequest.builder()
                .id(null)

                .nameOfCompany(duplicateVendorName)
                .vendorCode(null)

                .registeredUnder(vendorContext.getRegisteredUnder())

                .address1("Pune")
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

                .ownerName("AUTO_OWNER")

                // Reuse existing phone number to trigger duplicate validation
                .ownerPhoneNumber(vendorContext.getPhoneNumber())

                .ownerEmailId(TestDataGenerator.generateEmail())

                .serviceableArea("Across City")
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
            throw new IllegalStateException(
                    "Vendor must be created before approval.");
        }

        return ApproveVendorRequest.builder()
                .id(vendorContext.getVendorId())
                .nameOfCompany(vendorContext.getVendorName())
                .registeredUnder(vendorContext.getRegisteredUnder())

                .address1("Pune")
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
                .comments(vendorContext.getComments())

                .status("ACTIVE")

                .panCard(null)
                .upiPhoneNumber(null)
                .aadharNumber(null)

                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)

                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.emptyList())
                .documents(Collections.emptyList())
                .bankDetails(Collections.singletonList(buildDefaultBankDetails()))

                .requestType("APPROVAL_PAGE")

                .build();
    }

    public SearchVendorRequest buildSearchVendorRequest() {

        return SearchVendorRequest.builder()
                .searchText(vendorContext.getPhoneNumber())
                .userStatuses(List.of(
                        "NEW",
                        "DEACTIVATE",
                        "ACTIVE",
                        "IN_PROGRESS",
                        "DELETE",
                        "BANNED",
                        "CHECKINTODC"))
                .build();
    }

    public UpdateVendorRequest buildUpdateVendorRequest() {

        if (vendorContext.getVendorId() == null) {
            throw new IllegalStateException(
                    "Vendor must be created before update.");
        }

        String updatedVendorName = generateUpdatedVendorName();
        String updatedOwnerName = generateUpdatedOwnerName();
        String updatedComments = generateUpdatedComments();
        String updatedPhoneNumber = TestDataGenerator.generatePhoneNumber();

        vendorContext.setUpdatedVendorName(updatedVendorName);
        vendorContext.setUpdatedOwnerName(updatedOwnerName);
        vendorContext.setUpdatedPhoneNumber(updatedPhoneNumber);

        vendorContext.setUpdatedAddress1("Pune");
        vendorContext.setUpdatedCity(VendorConstants.CITY);
        vendorContext.setUpdatedCountry(VendorConstants.COUNTRY);
        vendorContext.setUpdatedZipCode(VendorConstants.ZIP_CODE);

        vendorContext.setUpdatedComments(updatedComments);
        vendorContext.setUpdatedRegisteredUnder("PARTNERSHIP");
        vendorContext.setUpdatedServiceableArea(
                vendorContext.getServiceableArea());

        vendorContext.setUpdatedDeliveryCenterId(
                VendorConstants.DEFAULT_DC_ID);

        return UpdateVendorRequest.builder()
                .id(vendorContext.getVendorId())
                .nameOfCompany(updatedVendorName)
                .registeredUnder(vendorContext.getUpdatedRegisteredUnder())

                .address1("Pune")
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

                .serviceableArea(vendorContext.getServiceableArea())
                .comments(updatedComments)

                .status("ACTIVE")

                .panCard(null)
                .upiPhoneNumber(null)
                .aadharNumber(null)

                .isTdsApplicable(false)
                .isPanVerified(false)
                .isBankDetailsVerified(false)
                .isPanAadharLinked(false)

                .dcModelList(Collections.singletonList(buildDefaultDc()))
                .vehicleList(Collections.singletonList(buildDefaultVehicle()))
                .vendorGstDetails(Collections.emptyList())
                .documents(Collections.emptyList())
                .bankDetails(Collections.singletonList(buildDefaultBankDetails()))

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

    private BankDetails buildDefaultBankDetails() {

        return BankDetails.builder()
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