package com.oorjaa.mdm.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class VendorContext {

    // =========================
    // Vendor Information
    // =========================
    private Integer vendorId;
    private Integer bankDetailsId;

    private String vendorName;
    private String ownerName;
    private String phoneNumber;

    private String address1;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    private String serviceableArea;
    private String comments;
    private String registeredUnder;

    private Integer deliveryCenterId;

    private String vendorCode;
    private Integer userId;

    // =========================
    // Update Vendor Information
    // =========================
    private String updatedVendorName;
    private String updatedOwnerName;
    private String updatedPhoneNumber;

    private String updatedAddress1;
    private String updatedCity;
    private String updatedCountry;
    private String updatedZipCode;

    private String updatedComments;
    private String updatedRegisteredUnder;
    private String updatedServiceableArea;

    private Integer updatedDeliveryCenterId;
    // Bank (create / update)
    private String accountNumber;
    private String ifscCode;           // routing_code
    private String accountHolderName;
    private String accountType;
    private String upiPhoneNumber;     // linked_phone_number
    private String bankName;
    private  String branchName;

    // getters/setters via Lombok @Getter @Setter on class
}