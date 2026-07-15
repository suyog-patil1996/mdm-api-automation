package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import com.oorjaa.mdm.repository.VendorRepository;
import com.oorjaa.mdm.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Component
public class VendorValidation {

    private final VendorContext vendorContext;
    private final VendorRepository vendorRepository;

    public VendorValidation(
            VendorContext vendorContext,
            VendorRepository vendorRepository) {

        this.vendorContext = vendorContext;
        this.vendorRepository = vendorRepository;
    }

    private String pass(Object expected, Object actual) {

        return String.format(
                "PASS%nExpected : %s%nActual   : %s%n%n",
                expected,
                actual);
    }

    private String generated(Object value) {

        return String.format(
                "PASS%nExpected : Generated%nActual   : %s%n%n",
                value);
    }

    @Step("Validate Vendor Creation Response")
    public void validateVendorCreation(Response response) {

        AllureHelper.addStep("Validate Create Vendor API Response");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== API VALIDATION ===============\n\n");

        validation.append(pass(
                200,
                response.getStatusCode()));

        Integer vendorId =
                response.jsonPath().getInt("data.id");

        validation.append(generated(vendorId));

        AllureHelper.attachValidationSummary(
                "Create Vendor API Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor creation failed.");

        assertNotNull(
                vendorId,
                "Vendor Id is null.");

        vendorContext.setVendorId(vendorId);

        AllureHelper.addStep(
                "Validate Vendor Record In Database");

        validateVendorCreationInDatabase();
    }
    @Step("Validate Duplicate Vendor Response")
    public void validateDuplicateVendor(Response response) {

        AllureHelper.addStep("Validate Duplicate Vendor Response");

        String message =
                response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== API VALIDATION ===============\n\n");

        validation.append(pass(
                500,
                response.getStatusCode()));

        validation.append(pass(
                "Phone number is already exist.",
                message));

        AllureHelper.attachValidationSummary(
                "Duplicate Vendor Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                500,
                "Duplicate vendor validation failed.");

        assertNotNull(
                message,
                "Duplicate validation message is missing.");

        assertEquals(
                message,
                "Phone number is already exist.",
                "Duplicate validation message mismatch.");
    }

    @Step("Validate Vendor Approval Response")
    public void validateVendorApproval(Response response) {

        AllureHelper.addStep("Validate Vendor Approval Response");

        String status =
                response.jsonPath().getString("status");

        Integer statusCode =
                response.jsonPath().getInt("statusCode");

        String message =
                response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== API VALIDATION ===============\n\n");

        validation.append(pass(
                200,
                response.getStatusCode()));

        validation.append(pass(
                "OK",
                status));

        validation.append(pass(
                200,
                statusCode));

        validation.append(pass(
                "Update vendor details successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Approve Vendor API Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor approval failed.");

        assertEquals(
                status,
                "OK",
                "Status mismatch.");

        assertEquals(
                statusCode,
                Integer.valueOf(200),
                "Status code mismatch.");

        assertEquals(
                message,
                "Update vendor details successfully.",
                "Approval message mismatch.");
    }

    @Step("Validate Vendor Search Response")
    public void validateVendorSearch(Response response) {

        AllureHelper.addStep("Validate Vendor Search Response");

        String status =
                response.jsonPath().getString("status");

        Integer statusCode =
                response.jsonPath().getInt("statusCode");

        String message =
                response.jsonPath().getString("message");

        Integer totalRecords =
                response.jsonPath().getInt("totalRecords");

        Integer vendorId =
                response.jsonPath().getInt("data.content[0].id");

        String vendorName =
                response.jsonPath().getString("data.content[0].nameOfCompany");

        String ownerName =
                response.jsonPath().getString("data.content[0].ownerName");

        String phoneNumber =
                response.jsonPath().getString("data.content[0].ownerPhoneNumber");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== API VALIDATION ===============\n\n");

        validation.append(pass(
                200,
                response.getStatusCode()));

        validation.append(pass(
                "OK",
                status));

        validation.append(pass(
                200,
                statusCode));

        validation.append(pass(
                "All Vendors Loaded Successfully",
                message));

        validation.append(pass(
                1,
                totalRecords));

        validation.append(pass(
                vendorContext.getVendorId(),
                vendorId));

        validation.append(pass(
                vendorContext.getVendorName(),
                vendorName));

        validation.append(pass(
                vendorContext.getOwnerName(),
                ownerName));

        validation.append(pass(
                vendorContext.getPhoneNumber(),
                phoneNumber));

        AllureHelper.attachValidationSummary(
                "Search Vendor API Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor search failed.");

        assertEquals(
                status,
                "OK",
                "Status mismatch.");

        assertEquals(
                statusCode,
                Integer.valueOf(200),
                "Status code mismatch.");

        assertEquals(
                message,
                "All Vendors Loaded Successfully",
                "Search message mismatch.");

        assertEquals(
                totalRecords,
                Integer.valueOf(1),
                "Unexpected number of vendors returned.");

        assertEquals(
                vendorId,
                vendorContext.getVendorId(),
                "Vendor Id mismatch.");

        assertEquals(
                vendorName,
                vendorContext.getVendorName(),
                "Vendor name mismatch.");

        assertEquals(
                ownerName,
                vendorContext.getOwnerName(),
                "Owner name mismatch.");

        assertEquals(
                phoneNumber,
                vendorContext.getPhoneNumber(),
                "Owner phone number mismatch.");
    }
    @Step("Validate Vendor Update Response")
    public void validateVendorUpdate(Response response) {

        AllureHelper.addStep("Validate Vendor Update Response");

        String status =
                response.jsonPath().getString("status");

        Integer statusCode =
                response.jsonPath().getInt("statusCode");

        String message =
                response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== API VALIDATION ===============\n\n");

        validation.append(pass(
                200,
                response.getStatusCode()));

        validation.append(pass(
                "OK",
                status));

        validation.append(pass(
                200,
                statusCode));

        validation.append(pass(
                "Update vendor details successfully.",
                message));

        AllureHelper.attachValidationSummary(
                "Update Vendor API Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor update failed.");

        assertEquals(
                status,
                "OK",
                "Status mismatch.");

        assertEquals(
                statusCode,
                Integer.valueOf(200),
                "Status code mismatch.");

        assertEquals(
                message,
                "Update vendor details successfully.",
                "Update message mismatch.");

        AllureHelper.addStep(
                "Validate Updated Vendor Record In Database");

        validateVendorUpdateInDatabase();
    }

    @Step("Validate Vendor Creation In Database")
    private void validateVendorCreationInDatabase() {

        VendorDetails vendorDetails =
                vendorRepository.getVendorDetails(
                        vendorContext.getVendorId());

        assertNotNull(
                vendorDetails,
                "Vendor record not found in database.");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(
                vendorContext.getVendorName(),
                vendorDetails.getNameOfCompany()));

        validation.append(pass(
                vendorContext.getOwnerName(),
                vendorDetails.getOwnerName()));

        validation.append(pass(
                "+91" + vendorContext.getPhoneNumber(),
                vendorDetails.getOwnerPhoneNumber()));

        validation.append(pass(
                vendorContext.getAddress1(),
                vendorDetails.getAddress1()));

        validation.append(pass(
                vendorContext.getCity(),
                vendorDetails.getCity()));

        validation.append(pass(
                vendorContext.getCountry(),
                vendorDetails.getCountry()));

        validation.append(pass(
                vendorContext.getZipCode(),
                vendorDetails.getZipCode()));

        validation.append(pass(
                vendorContext.getRegisteredUnder(),
                vendorDetails.getRegisteredUnder()));

        validation.append(pass(
                vendorContext.getServiceableArea(),
                vendorDetails.getServiceableArea()));

        validation.append(pass(
                vendorContext.getComments(),
                vendorDetails.getComments()));

        validation.append(generated(
                vendorDetails.getVendorCode()));

        validation.append(generated(
                vendorDetails.getUserId()));

        validation.append(generated(
                vendorDetails.getDeliveryCenterId()));

        AllureHelper.attachValidationSummary(
                "Create Vendor Database Validation",
                validation.toString());

        assertEquals(vendorDetails.getNameOfCompany(), vendorContext.getVendorName());
        assertEquals(vendorDetails.getOwnerName(), vendorContext.getOwnerName());
        assertEquals(vendorDetails.getOwnerPhoneNumber(), "+91" + vendorContext.getPhoneNumber());
        assertEquals(vendorDetails.getAddress1(), vendorContext.getAddress1());
        assertEquals(vendorDetails.getCity(), vendorContext.getCity());
        assertEquals(vendorDetails.getCountry(), vendorContext.getCountry());
        assertEquals(vendorDetails.getZipCode(), vendorContext.getZipCode());
        assertEquals(vendorDetails.getRegisteredUnder(), vendorContext.getRegisteredUnder());
        assertEquals(vendorDetails.getServiceableArea(), vendorContext.getServiceableArea());
        assertEquals(vendorDetails.getComments(), vendorContext.getComments());

        assertNotNull(vendorDetails.getVendorCode());
        assertNotNull(vendorDetails.getUserId());
        assertNotNull(vendorDetails.getDeliveryCenterId());
    }

    @Step("Validate Vendor Update In Database")
    private void validateVendorUpdateInDatabase() {

        VendorDetails vendorDetails =
                vendorRepository.getVendorDetails(
                        vendorContext.getVendorId());

        assertNotNull(
                vendorDetails,
                "Vendor record not found in database.");

        StringBuilder validation = new StringBuilder();

        validation.append("=============== DATABASE VALIDATION ===============\n\n");

        validation.append(pass(
                vendorContext.getUpdatedVendorName(),
                vendorDetails.getNameOfCompany()));

        validation.append(pass(
                vendorContext.getUpdatedOwnerName(),
                vendorDetails.getOwnerName()));

        validation.append(pass(
                vendorContext.getUpdatedPhoneNumber(),
                vendorDetails.getOwnerPhoneNumber()));

        validation.append(pass(
                vendorContext.getUpdatedAddress1(),
                vendorDetails.getAddress1()));

        validation.append(pass(
                vendorContext.getUpdatedCity(),
                vendorDetails.getCity()));

        validation.append(pass(
                vendorContext.getUpdatedCountry(),
                vendorDetails.getCountry()));

        validation.append(pass(
                vendorContext.getUpdatedZipCode(),
                vendorDetails.getZipCode()));

        validation.append(pass(
                vendorContext.getUpdatedRegisteredUnder(),
                vendorDetails.getRegisteredUnder()));

        validation.append(pass(
                vendorContext.getUpdatedServiceableArea(),
                vendorDetails.getServiceableArea()));

        validation.append(pass(
                vendorContext.getUpdatedComments(),
                vendorDetails.getComments()));

        validation.append(generated(
                vendorDetails.getVendorCode()));

        validation.append(generated(
                vendorDetails.getUserId()));

        validation.append(generated(
                vendorDetails.getDeliveryCenterId()));

        AllureHelper.attachValidationSummary(
                "Update Vendor Database Validation",
                validation.toString());

        assertEquals(vendorDetails.getNameOfCompany(), vendorContext.getUpdatedVendorName());
        assertEquals(vendorDetails.getOwnerName(), vendorContext.getUpdatedOwnerName());
        assertEquals(vendorDetails.getOwnerPhoneNumber(), vendorContext.getUpdatedPhoneNumber());
        assertEquals(vendorDetails.getAddress1(), vendorContext.getUpdatedAddress1());
        assertEquals(vendorDetails.getCity(), vendorContext.getUpdatedCity());
        assertEquals(vendorDetails.getCountry(), vendorContext.getUpdatedCountry());
        assertEquals(vendorDetails.getZipCode(), vendorContext.getUpdatedZipCode());
        assertEquals(vendorDetails.getRegisteredUnder(), vendorContext.getUpdatedRegisteredUnder());
        assertEquals(vendorDetails.getServiceableArea(), vendorContext.getUpdatedServiceableArea());
        assertEquals(vendorDetails.getComments(), vendorContext.getUpdatedComments());

        assertNotNull(vendorDetails.getVendorCode());
        assertNotNull(vendorDetails.getUserId());
        assertNotNull(vendorDetails.getDeliveryCenterId());
    }

}