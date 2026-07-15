package com.oorjaa.mdm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.api.VendorAPI;
import com.oorjaa.mdm.model.vendor.request.ApproveVendorRequest;
import com.oorjaa.mdm.model.vendor.request.CreateVendorRequest;
import com.oorjaa.mdm.model.vendor.request.SearchVendorRequest;
import com.oorjaa.mdm.model.vendor.request.UpdateVendorRequest;
import com.oorjaa.mdm.payload.VendorPayloadBuilder;
import com.oorjaa.mdm.utils.AllureHelper;
import com.oorjaa.mdm.validation.VendorValidation;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    private final VendorAPI vendorAPI;
    private final VendorPayloadBuilder payloadBuilder;
    private final VendorValidation vendorValidation;
    private final ObjectMapper objectMapper;

    public VendorService(VendorAPI vendorAPI,
                         VendorPayloadBuilder payloadBuilder,
                         VendorValidation vendorValidation,
                         ObjectMapper objectMapper) {

        this.vendorAPI = vendorAPI;
        this.payloadBuilder = payloadBuilder;
        this.vendorValidation = vendorValidation;
        this.objectMapper = objectMapper;
    }

    /**
     * Create Vendor
     */
    public void createVendor() {

        AllureHelper.addStep("Create Vendor");

        CreateVendorRequest request =
                payloadBuilder.buildCreateVendorRequest();

        attachVendorInformation(request);

        attachRequest("Create Vendor", request);

        Response response =
                vendorAPI.createVendor(request);

        AllureHelper.attachResponse(
                "Create Vendor",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        vendorValidation.validateVendorCreation(response);
    }

    /**
     * Duplicate Mobile Validation
     */
    public void validateDuplicatePhoneNumber() {

        AllureHelper.addStep("Duplicate Mobile Number Validation");

        CreateVendorRequest request =
                payloadBuilder.buildDuplicateVendorRequest();

        attachRequest("Duplicate Vendor", request);

        Response response =
                vendorAPI.createVendor(request);

        AllureHelper.attachResponse(
                "Duplicate Vendor",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        vendorValidation.validateDuplicateVendor(response);
    }

    /**
     * Approve Vendor
     */
    public void approveVendor() {

        AllureHelper.addStep("Approve Vendor");

        ApproveVendorRequest request =
                payloadBuilder.buildApproveVendorRequest();

        attachRequest("Approve Vendor", request);

        Response response =
                vendorAPI.approveVendor(request);

        AllureHelper.attachResponse(
                "Approve Vendor",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        vendorValidation.validateVendorApproval(response);
    }

    /**
     * Search Vendor
     */
    public void searchVendor() {

        AllureHelper.addStep("Search Vendor");

        SearchVendorRequest request =
                payloadBuilder.buildSearchVendorRequest();

        attachRequest("Search Vendor", request);

        Response response =
                vendorAPI.searchVendor(request);

        AllureHelper.attachResponse(
                "Search Vendor",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        vendorValidation.validateVendorSearch(response);
    }

    /**
     * Update Vendor
     */
    public void updateVendor() {

        AllureHelper.addStep("Update Vendor");

        UpdateVendorRequest request =
                payloadBuilder.buildUpdateVendorRequest();

        attachRequest("Update Vendor", request);

        Response response =
                vendorAPI.updateVendor(request);

        AllureHelper.attachResponse(
                "Update Vendor",
                response.asPrettyString());

        AllureHelper.attachResponseDetails(
                response.getStatusCode(),
                response.time());

        vendorValidation.validateVendorUpdate(response);
    }

    private void attachRequest(String operation, Object request) {

        try {

            AllureHelper.attachRequest(
                    operation,
                    objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(request));

        } catch (JsonProcessingException e) {

            AllureHelper.attachException(e);
        }
    }

    private void attachVendorInformation(CreateVendorRequest request) {

        StringBuilder builder = new StringBuilder();

        builder.append("Vendor Name : ")
                .append(request.getNameOfCompany())
                .append(System.lineSeparator());

        builder.append("Mobile : ")
                .append(request.getOwnerPhoneNumber())
                .append(System.lineSeparator());

        builder.append("Email : ")
                .append(request.getOwnerEmailId())
                .append(System.lineSeparator());

        builder.append("State : ")
                .append(request.getState())
                .append(System.lineSeparator());

        builder.append("City : ")
                .append(request.getCity())
                .append(System.lineSeparator());

          AllureHelper.attachBusinessData(
                "Vendor Basic Information",
                builder.toString());
    }

}