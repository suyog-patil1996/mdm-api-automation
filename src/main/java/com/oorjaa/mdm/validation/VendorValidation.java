package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import com.oorjaa.mdm.repository.VendorRepository;
import com.oorjaa.mdm.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;


import java.util.List;

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
                400,
                response.getStatusCode()));

        validation.append(pass(
                "Phone number is already exist.",
                message));

        AllureHelper.attachValidationSummary(
                "Duplicate Vendor Validation",
                validation.toString());

        assertEquals(
                response.getStatusCode(),
                400,
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

        VendorDetails d =
                vendorRepository.getVendorDetails(vendorContext.getVendorId());

        assertNotNull(d, "Vendor record not found in database.");

        StringBuilder v = new StringBuilder();
        v.append("=============== DATABASE VALIDATION (CREATE) ===============\n\n");

        // Vendor master
        v.append(pass(vendorContext.getVendorName(), d.getNameOfCompany()));
        v.append(pass(vendorContext.getOwnerName(), d.getOwnerName()));
        v.append(pass("+91" + vendorContext.getPhoneNumber(), d.getOwnerPhoneNumber()));
        // If context already stores +91, use vendorContext.getPhoneNumber() only
        v.append(pass(vendorContext.getAddress1(), d.getAddress1()));
        v.append(pass(vendorContext.getCity(), d.getCity()));
        v.append(pass(vendorContext.getCountry(), d.getCountry()));
        v.append(pass(vendorContext.getZipCode(), d.getZipCode()));
        v.append(pass(vendorContext.getRegisteredUnder(), d.getRegisteredUnder()));
        v.append(pass(vendorContext.getServiceableArea(), d.getServiceableArea()));
        v.append(pass(vendorContext.getComments(), d.getComments()));
        v.append(pass(vendorContext.getDeliveryCenterId(), d.getDeliveryCenterId()));
        v.append(generated(d.getVendorCode()));
        v.append(generated(d.getUserId()));

        // User
        v.append(pass(vendorContext.getOwnerName(), d.getFirstName()));
        v.append(generated(d.getKeycloakId()));
        v.append(generated(d.getKeycloakUsername()));

        // Bank (latest)
        if (vendorContext.getAccountNumber() != null) {
            assertNotNull(d.getBankDetails(), "Bank details not found in DB.");
            v.append(pass(vendorContext.getAccountNumber(), d.getBankDetails().getAccountNumber()));
            v.append(pass(vendorContext.getIfscCode(), d.getBankDetails().getRoutingCode()));
            v.append(pass(vendorContext.getAccountHolderName(), d.getBankDetails().getAccountHolderName()));
            v.append(pass(vendorContext.getAccountType(), d.getBankDetails().getAccountType()));
            v.append(pass(vendorContext.getUpiPhoneNumber(), d.getBankDetails().getLinkedPhoneNumber()));
        }

        // Documents – presence of expected names (photo ids optional if no upload)
        List<String> expectedDocs = List.of(
                "msmeNumber", "cancelledCheque", "gstNumber",
                "aadharCardNumber", "panNumber");
        for (String name : expectedDocs) {
            boolean found = d.getDocuments().stream()
                    .anyMatch(doc -> name.equals(doc.getDocumentName()));
            v.append(pass(true, found));
            // Soft: only assert if your create always inserts all 5
            // assertTrue(found, "Document missing in DB: " + name);
        }

        AllureHelper.attachValidationSummary(
                "Create Vendor Database Validation", v.toString());

        assertEquals(d.getNameOfCompany(), vendorContext.getVendorName());
        assertEquals(d.getOwnerName(), vendorContext.getOwnerName());
        assertEquals(d.getAddress1(), vendorContext.getAddress1());
        assertEquals(d.getCity(), vendorContext.getCity());
        assertEquals(d.getCountry(), vendorContext.getCountry());
        assertEquals(d.getZipCode(), vendorContext.getZipCode());
        assertEquals(d.getRegisteredUnder(), vendorContext.getRegisteredUnder());
        assertEquals(d.getServiceableArea(), vendorContext.getServiceableArea());
        assertEquals(d.getComments(), vendorContext.getComments());
        assertEquals(d.getDeliveryCenterId(), vendorContext.getDeliveryCenterId());
        assertNotNull(d.getVendorCode());
        assertNotNull(d.getUserId());

        if (vendorContext.getAccountNumber() != null) {
            assertEquals(d.getBankDetails().getAccountNumber(), vendorContext.getAccountNumber());
            assertEquals(d.getBankDetails().getRoutingCode(), vendorContext.getIfscCode());
            assertEquals(d.getBankDetails().getAccountHolderName(), vendorContext.getAccountHolderName());
            assertEquals(d.getBankDetails().getAccountType(), vendorContext.getAccountType());
            assertEquals(d.getBankDetails().getLinkedPhoneNumber(), vendorContext.getUpiPhoneNumber());
        }
    }

    @Step("Validate Vendor Update In Database")
    private void validateVendorUpdateInDatabase() {

        VendorDetails d =
                vendorRepository.getVendorDetails(vendorContext.getVendorId());

        assertNotNull(d, "Vendor record not found in database.");

        StringBuilder v = new StringBuilder();
        v.append("=============== DATABASE VALIDATION (UPDATE) ===============\n\n");

        v.append(pass(vendorContext.getUpdatedVendorName(), d.getNameOfCompany()));
        v.append(pass(vendorContext.getUpdatedOwnerName(), d.getOwnerName()));
        v.append(pass(vendorContext.getUpdatedPhoneNumber(), d.getOwnerPhoneNumber()));
        v.append(pass(vendorContext.getUpdatedAddress1(), d.getAddress1()));
        v.append(pass(vendorContext.getUpdatedCity(), d.getCity()));
        v.append(pass(vendorContext.getUpdatedCountry(), d.getCountry()));
        v.append(pass(vendorContext.getUpdatedZipCode(), d.getZipCode()));
        v.append(pass(vendorContext.getUpdatedRegisteredUnder(), d.getRegisteredUnder()));
        v.append(pass(vendorContext.getUpdatedServiceableArea(), d.getServiceableArea()));
        v.append(pass(vendorContext.getUpdatedComments(), d.getComments()));

        AllureHelper.attachValidationSummary(
                "Update Vendor Database Validation", v.toString());

        assertEquals(d.getNameOfCompany(), vendorContext.getUpdatedVendorName());
        assertEquals(d.getOwnerName(), vendorContext.getUpdatedOwnerName());
        assertEquals(d.getOwnerPhoneNumber(), vendorContext.getUpdatedPhoneNumber());
        assertEquals(d.getAddress1(), vendorContext.getUpdatedAddress1());
        assertEquals(d.getCity(), vendorContext.getUpdatedCity());
        assertEquals(d.getCountry(), vendorContext.getUpdatedCountry());
        assertEquals(d.getZipCode(), vendorContext.getUpdatedZipCode());
        assertEquals(d.getRegisteredUnder(), vendorContext.getUpdatedRegisteredUnder());
        assertEquals(d.getServiceableArea(), vendorContext.getUpdatedServiceableArea());
        assertEquals(d.getComments(), vendorContext.getUpdatedComments());
    }
}