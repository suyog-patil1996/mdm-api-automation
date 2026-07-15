package com.oorjaa.mdm.service;

import com.oorjaa.mdm.api.VendorAPI;
import com.oorjaa.mdm.model.vendor.request.ApproveVendorRequest;
import com.oorjaa.mdm.model.vendor.request.CreateVendorRequest;
import com.oorjaa.mdm.model.vendor.request.SearchVendorRequest;
import com.oorjaa.mdm.model.vendor.request.UpdateVendorRequest;
import com.oorjaa.mdm.payload.VendorPayloadBuilder;
import com.oorjaa.mdm.validation.VendorValidation;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    private final VendorAPI vendorAPI;
    private final VendorPayloadBuilder payloadBuilder;
    private final VendorValidation vendorValidation;

    public VendorService(VendorAPI vendorAPI,
                         VendorPayloadBuilder payloadBuilder,
                         VendorValidation vendorValidation) {

        this.vendorAPI = vendorAPI;
        this.payloadBuilder = payloadBuilder;
        this.vendorValidation = vendorValidation;
    }

    /**
     * Create Vendor
     */
    public void createVendor() {

        CreateVendorRequest request =
                payloadBuilder.buildCreateVendorRequest();

        Response response =
                vendorAPI.createVendor(request);

        vendorValidation.validateVendorCreation(response);
    }

    public void validateDuplicatePhoneNumber() {

        CreateVendorRequest request =
                payloadBuilder.buildDuplicateVendorRequest();

        Response response =
                vendorAPI.createVendor(request);

        vendorValidation.validateDuplicateVendor(response);
    }

    public void approveVendor() {

        ApproveVendorRequest request =
                payloadBuilder.buildApproveVendorRequest();

        Response response =
                vendorAPI.approveVendor(request);

        vendorValidation.validateVendorApproval(response);
    }

    public void searchVendor() {

        SearchVendorRequest request =
                payloadBuilder.buildSearchVendorRequest();

        Response response =
                vendorAPI.searchVendor(request);

        vendorValidation.validateVendorSearch(response);
    }

    public void updateVendor() {

        UpdateVendorRequest request =
                payloadBuilder.buildUpdateVendorRequest();

        Response response =
                vendorAPI.updateVendor(request);

        vendorValidation.validateVendorUpdate(response);
    }

}