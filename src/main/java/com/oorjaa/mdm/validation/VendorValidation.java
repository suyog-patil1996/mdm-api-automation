package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import com.oorjaa.mdm.model.vendor.VendorDocumentRow;
import com.oorjaa.mdm.repository.VendorRepository;
import com.oorjaa.mdm.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Component
public class VendorValidation {

    private final VendorContext vendorContext;
    private final VendorRepository vendorRepository;

    /** documentName values from UI / API payload */
    private static final List<String> EXPECTED_DOCUMENTS = Arrays.asList(
            "msmeNumber",
            "panNumber",
            "cancelledCheque",
            "gstNumber",
            "aadharCardNumber"
    );

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

    private String failMsg(String label, Object expected, Object actual) {
        return String.format("%s | Expected: %s | Actual: %s", label, expected, actual);
    }

    /**
     * DB may store phone with or without +91.
     */
    private String normalizePhone(String phone) {
        if (phone == null) {
            return null;
        }
        String p = phone.trim();
        if (p.startsWith("+91")) {
            return p;
        }
        if (p.startsWith("91") && p.length() > 10) {
            return "+" + p;
        }
        if (p.length() == 10) {
            return "+91" + p;
        }
        return p;
    }

    // -------------------------------------------------------------------------
    // CREATE API
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Creation Response")
    public void validateVendorCreation(Response response) {

        AllureHelper.addStep("Validate Create Vendor API Response");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));

        Integer vendorId = response.jsonPath().getInt("data.id");
        validation.append(generated(vendorId));

        AllureHelper.attachValidationSummary(
                "Create Vendor API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Vendor creation failed.");
        assertNotNull(vendorId, "Vendor Id is null.");

        vendorContext.setVendorId(vendorId);

        AllureHelper.addStep("Validate Vendor Record In Database");
        validateVendorCreationInDatabase();
    }

    // -------------------------------------------------------------------------
    // DUPLICATE
    // -------------------------------------------------------------------------

    @Step("Validate Duplicate Vendor Response")
    public void validateDuplicateVendor(Response response) {

        AllureHelper.addStep("Validate Duplicate Vendor Response");

        int status = response.getStatusCode();
        String message = response.jsonPath().getString("message");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass("not 200", status));
        validation.append(pass("error message present", message));

        AllureHelper.attachValidationSummary(
                "Duplicate Vendor Validation",
                validation.toString());

        assertTrue(status != 200, "Duplicate vendor should not return 200. Status=" + status);
        assertNotNull(message, "Duplicate message is null.");
    }

    // -------------------------------------------------------------------------
    // APPROVE
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Approval Response")
    public void validateVendorApproval(Response response) {

        AllureHelper.addStep("Validate Vendor Approval Response");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));

        AllureHelper.attachValidationSummary(
                "Approve Vendor API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Vendor approval failed.");
    }

    // -------------------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Search Response")
    public void validateVendorSearch(Response response) {

        AllureHelper.addStep("Validate Vendor Search Response");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));

        assertEquals(response.getStatusCode(), 200, "Vendor search failed.");

        Integer expectedId = vendorContext.getVendorId();
        assertNotNull(expectedId, "Vendor Id is null in context.");

        // Try common list paths
        List<Integer> ids = response.jsonPath().getList("data.content.id");
        if (ids == null || ids.isEmpty()) {
            ids = response.jsonPath().getList("data.vendor.id");
        }
        if (ids == null || ids.isEmpty()) {
            ids = response.jsonPath().getList("data.id");
        }

        boolean found = ids != null && ids.stream().anyMatch(id -> expectedId.equals(id));
        validation.append(pass(true, found));

        AllureHelper.attachValidationSummary(
                "Search Vendor API Validation",
                validation.toString());

        assertTrue(found, "Created vendor not found in search. Id=" + expectedId);
    }

    // -------------------------------------------------------------------------
    // UPDATE API
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Update Response")
    public void validateVendorUpdate(Response response) {

        AllureHelper.addStep("Validate Vendor Update Response");

        StringBuilder validation = new StringBuilder();
        validation.append("=============== API VALIDATION ===============\n\n");
        validation.append(pass(200, response.getStatusCode()));

        AllureHelper.attachValidationSummary(
                "Update Vendor API Validation",
                validation.toString());

        assertEquals(response.getStatusCode(), 200, "Vendor update failed.");

        AllureHelper.addStep("Validate Updated Vendor Record In Database");
        validateVendorUpdateInDatabase();
    }

    // -------------------------------------------------------------------------
    // DATABASE – CREATE
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Creation In Database")
    private void validateVendorCreationInDatabase() {

        VendorDetails d =
                vendorRepository.getVendorDetails(vendorContext.getVendorId());

        assertNotNull(d, "Vendor record not found in database.");

        StringBuilder v = new StringBuilder();
        v.append("=============== DATABASE VALIDATION (CREATE) ===============\n\n");

        // ----- Vendor master -----
        v.append(pass(vendorContext.getVendorName(), d.getNameOfCompany()));
        v.append(pass(vendorContext.getOwnerName(), d.getOwnerName()));

        String expectedPhone = normalizePhone(vendorContext.getPhoneNumber());
        String actualPhone = normalizePhone(d.getOwnerPhoneNumber());
        v.append(pass(expectedPhone, actualPhone));

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

        // ----- User -----
        v.append(pass(vendorContext.getOwnerName(), d.getFirstName()));
        v.append(generated(d.getKeycloakId()));
        v.append(generated(d.getKeycloakUsername()));

        // ----- Bank (latest) -----
        if (vendorContext.getAccountNumber() != null) {
            assertNotNull(d.getBankDetails(), "Bank details not found in DB.");
            v.append(pass(vendorContext.getAccountNumber(), d.getBankDetails().getAccountNumber()));
            v.append(pass(vendorContext.getIfscCode(), d.getBankDetails().getRoutingCode()));
            v.append(pass(vendorContext.getAccountHolderName(), d.getBankDetails().getAccountHolderName()));
            v.append(pass(vendorContext.getAccountType(), d.getBankDetails().getAccountType()));
            if (vendorContext.getUpiPhoneNumber() != null) {
                v.append(pass(
                        vendorContext.getUpiPhoneNumber(),
                        d.getBankDetails().getLinkedPhoneNumber()));
            }
        }

        // ----- Documents (UI documentName list) -----
        v.append("\n----- DOCUMENTS -----\n\n");
        assertNotNull(d.getDocuments(), "Documents list is null in DB.");

        for (String docName : EXPECTED_DOCUMENTS) {
            Optional<VendorDocumentRow> row = d.getDocuments().stream()
                    .filter(x -> docName.equals(x.getDocumentName()))
                    .findFirst();

            boolean present = row.isPresent();
            v.append(pass(docName + " present", present));

            assertTrue(present, "Document missing in DB: " + docName);

            // Photo ids – assert not null when files were uploaded
            // Aadhaar typically has front (+ back)
            if ("aadharCardNumber".equals(docName) && row.isPresent()) {
                v.append(pass("aadhar front_photo_id not null", row.get().getFrontPhotoId() != null));
                // back may be optional depending on API
                v.append(generated(row.get().getBackPhotoId()));
            }
            if ("panNumber".equals(docName) && row.isPresent()) {
                v.append(pass("pan front_photo_id not null", row.get().getFrontPhotoId() != null));
            }
            if ("cancelledCheque".equals(docName) && row.isPresent()) {
                v.append(pass("cheque front_photo_id not null", row.get().getFrontPhotoId() != null));
            }
            if ("gstNumber".equals(docName) && row.isPresent()) {
                v.append(pass("gst front_photo_id not null", row.get().getFrontPhotoId() != null));
            }
            if ("msmeNumber".equals(docName) && row.isPresent()) {
                v.append(pass("msme front_photo_id not null", row.get().getFrontPhotoId() != null));
            }
        }

        AllureHelper.attachValidationSummary(
                "Create Vendor Database Validation",
                v.toString());

        // Hard asserts – master
        assertEquals(d.getNameOfCompany(), vendorContext.getVendorName());
        assertEquals(d.getOwnerName(), vendorContext.getOwnerName());
        assertEquals(actualPhone, expectedPhone, failMsg("owner phone", expectedPhone, actualPhone));
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

        // Hard asserts – bank
        if (vendorContext.getAccountNumber() != null) {
            assertEquals(d.getBankDetails().getAccountNumber(), vendorContext.getAccountNumber());
            assertEquals(d.getBankDetails().getRoutingCode(), vendorContext.getIfscCode());
            assertEquals(d.getBankDetails().getAccountHolderName(), vendorContext.getAccountHolderName());
            assertEquals(d.getBankDetails().getAccountType(), vendorContext.getAccountType());
            if (vendorContext.getUpiPhoneNumber() != null
                    && d.getBankDetails().getLinkedPhoneNumber() != null) {
                assertEquals(
                        d.getBankDetails().getLinkedPhoneNumber(),
                        vendorContext.getUpiPhoneNumber());
            }
        }
    }

    // -------------------------------------------------------------------------
    // DATABASE – UPDATE
    // -------------------------------------------------------------------------

    @Step("Validate Vendor Update In Database")
    private void validateVendorUpdateInDatabase() {

        VendorDetails d =
                vendorRepository.getVendorDetails(vendorContext.getVendorId());

        assertNotNull(d, "Vendor record not found in database.");

        StringBuilder v = new StringBuilder();
        v.append("=============== DATABASE VALIDATION (UPDATE) ===============\n\n");

        v.append(pass(vendorContext.getUpdatedVendorName(), d.getNameOfCompany()));
        v.append(pass(vendorContext.getUpdatedOwnerName(), d.getOwnerName()));

        String expectedPhone = normalizePhone(vendorContext.getUpdatedPhoneNumber());
        String actualPhone = normalizePhone(d.getOwnerPhoneNumber());
        v.append(pass(expectedPhone, actualPhone));

        v.append(pass(vendorContext.getUpdatedAddress1(), d.getAddress1()));
        v.append(pass(vendorContext.getUpdatedCity(), d.getCity()));
        v.append(pass(vendorContext.getUpdatedCountry(), d.getCountry()));
        v.append(pass(vendorContext.getUpdatedZipCode(), d.getZipCode()));
        v.append(pass(vendorContext.getUpdatedRegisteredUnder(), d.getRegisteredUnder()));
        v.append(pass(vendorContext.getUpdatedServiceableArea(), d.getServiceableArea()));
        v.append(pass(vendorContext.getUpdatedComments(), d.getComments()));

        // Bank after update (if context still has bank values from last bank build)
        if (vendorContext.getAccountNumber() != null && d.getBankDetails() != null) {
            v.append(pass(vendorContext.getAccountNumber(), d.getBankDetails().getAccountNumber()));
            v.append(pass(vendorContext.getIfscCode(), d.getBankDetails().getRoutingCode()));
            v.append(pass(vendorContext.getAccountHolderName(), d.getBankDetails().getAccountHolderName()));
            v.append(pass(vendorContext.getAccountType(), d.getBankDetails().getAccountType()));
        }

        // Documents should still exist after update
        v.append("\n----- DOCUMENTS (after update) -----\n\n");
        if (d.getDocuments() != null) {
            for (String docName : EXPECTED_DOCUMENTS) {
                boolean present = d.getDocuments().stream()
                        .anyMatch(x -> docName.equals(x.getDocumentName()));
                v.append(pass(docName + " present", present));
                assertTrue(present, "Document missing after update: " + docName);
            }
        }

        AllureHelper.attachValidationSummary(
                "Update Vendor Database Validation",
                v.toString());

        assertEquals(d.getNameOfCompany(), vendorContext.getUpdatedVendorName());
        assertEquals(d.getOwnerName(), vendorContext.getUpdatedOwnerName());
        assertEquals(actualPhone, expectedPhone, failMsg("updated phone", expectedPhone, actualPhone));
        assertEquals(d.getAddress1(), vendorContext.getUpdatedAddress1());
        assertEquals(d.getCity(), vendorContext.getUpdatedCity());
        assertEquals(d.getCountry(), vendorContext.getUpdatedCountry());
        assertEquals(d.getZipCode(), vendorContext.getUpdatedZipCode());
        assertEquals(d.getRegisteredUnder(), vendorContext.getUpdatedRegisteredUnder());
        assertEquals(d.getServiceableArea(), vendorContext.getUpdatedServiceableArea());
        assertEquals(d.getComments(), vendorContext.getUpdatedComments());
    }
}