package com.oorjaa.mdm.model.vendor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVendorRequest {

    private Integer id;


    private String nameOfCompany;
    private String vendorCode;
    private String registeredUnder;

    private String address1;
    private String address2;
    private String landmark;

    private String cityId;
    private String countryId;
    private String stateId;

    private String country;
    private String state;
    private String city;
    private String zipCode;

    private String msmeNumber;
    private String vendorType;
    private String commercialId;
    private String smeRegistrationNumber;

    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerEmailId;

    private String operationPointOfContact;
    private String operationEmailId;

    private String financePointOfContact;
    private String financeEmailId;

    private List<DcModel> dcModelList;

    private String serviceableArea;
    private String comments;

    private List<VehicleModel> vehicleList;

    private String status;

    private String panCard;
    private String upiPhoneNumber;
    private String aadharNumber;

    private Boolean isTdsApplicable;
    private Double tdsPercentage;

    private String bankAccountDetails;
    private String panDetails;

    private Boolean isPanVerified;
    private Boolean isBankDetailsVerified;
    private Boolean isPanAadharLinked;

    private List<VendorGstDetail> vendorGstDetails;

    private List<Document> documents;

    private List<BankDetails> bankDetails;
}