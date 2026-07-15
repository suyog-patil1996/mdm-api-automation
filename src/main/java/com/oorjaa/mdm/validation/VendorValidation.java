package com.oorjaa.mdm.validation;

import com.oorjaa.mdm.context.VendorContext;
import com.oorjaa.mdm.repository.VendorRepository;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;
import com.oorjaa.mdm.model.vendor.VendorDetails;

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

    @Step("Validate Vendor Creation Response")
    public void validateVendorCreation(Response response) {

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor creation failed.");

        Integer vendorId =
                response.jsonPath().getInt("data.id");

        assertNotNull(
                vendorId,
                "Vendor Id is null.");

        vendorContext.setVendorId(vendorId);

        validateVendorCreationInDatabase();
    }

    @Step("Validate Vendor Approval Response")
    public void validateVendorApproval(Response response) {

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor approval failed.");

        String status =
                response.jsonPath().getString("status");

        Integer statusCode =
                response.jsonPath().getInt("statusCode");

        String message =
                response.jsonPath().getString("message");

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

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor search failed.");

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

        assertEquals(
                response.getStatusCode(),
                200,
                "Vendor update failed.");

        String status =
                response.jsonPath().getString("status");

        Integer statusCode =
                response.jsonPath().getInt("statusCode");

        String message =
                response.jsonPath().getString("message");

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

        validateVendorUpdateInDatabase();
    }


    @Step("Validate Duplicate Vendor Response")
    public void validateDuplicateVendor(Response response) {

        assertEquals(
                response.getStatusCode(),
                500,
                "Duplicate vendor validation failed.");

        String message =
                response.jsonPath().getString("message");

        assertNotNull(
                message,
                "Duplicate validation message is missing.");

        assertEquals(
                message,
                "Phone number is already exist.",
                "Duplicate validation message mismatch.");
    }
    @Step("Validate Vendor Creation In Database")
    private void validateVendorCreationInDatabase() {

        VendorDetails vendorDetails =
                vendorRepository.getVendorDetails(
                        vendorContext.getVendorId());

        assertNotNull(
                vendorDetails,
                "Vendor record not found in database.");

        assertEquals(
                vendorDetails.getNameOfCompany(),
                vendorContext.getVendorName(),
                "Vendor name mismatch.");

        assertEquals(
                vendorDetails.getOwnerName(),
                vendorContext.getOwnerName(),
                "Owner name mismatch.");

        assertEquals(
                vendorDetails.getOwnerPhoneNumber(),
                "+91" + vendorContext.getPhoneNumber(),
                "Owner phone number mismatch.");

        assertEquals(
                vendorDetails.getAddress1(),
                vendorContext.getAddress1(),
                "Address mismatch.");

        assertEquals(
                vendorDetails.getCity(),
                vendorContext.getCity(),
                "City mismatch.");

        assertEquals(
                vendorDetails.getCountry(),
                vendorContext.getCountry(),
                "Country mismatch.");

        assertEquals(
                vendorDetails.getZipCode(),
                vendorContext.getZipCode(),
                "Zip Code mismatch.");

        assertEquals(
                vendorDetails.getRegisteredUnder(),
                vendorContext.getRegisteredUnder(),
                "Registered Under mismatch.");

        assertEquals(
                vendorDetails.getServiceableArea(),
                vendorContext.getServiceableArea(),
                "Serviceable Area mismatch.");

        assertEquals(
                vendorDetails.getComments(),
                vendorContext.getComments(),
                "Comments mismatch.");

        assertNotNull(
                vendorDetails.getVendorCode(),
                "Vendor Code was not generated.");

        assertNotNull(
                vendorDetails.getUserId(),
                "User Id was not generated.");

        assertNotNull(
                vendorDetails.getDeliveryCenterId(),
                "Delivery Center was not created.");
    }
    @Step("Validate Vendor Update In Database")
    private void validateVendorUpdateInDatabase() {

        VendorDetails vendorDetails =
                vendorRepository.getVendorDetails(
                        vendorContext.getVendorId());

        assertNotNull(
                vendorDetails,
                "Vendor record not found in database.");

        assertEquals(
                vendorDetails.getNameOfCompany(),
                vendorContext.getUpdatedVendorName(),
                "Updated vendor name mismatch.");

        assertEquals(
                vendorDetails.getOwnerName(),
                vendorContext.getUpdatedOwnerName(),
                "Updated owner name mismatch.");

        assertEquals(
                vendorDetails.getOwnerPhoneNumber(),
                vendorContext.getUpdatedPhoneNumber(),
                "Updated phone number mismatch.");

        assertEquals(
                vendorDetails.getAddress1(),
                vendorContext.getUpdatedAddress1(),
                "Updated address mismatch.");

        assertEquals(
                vendorDetails.getCity(),
                vendorContext.getUpdatedCity(),
                "Updated city mismatch.");

        assertEquals(
                vendorDetails.getCountry(),
                vendorContext.getUpdatedCountry(),
                "Updated country mismatch.");

        assertEquals(
                vendorDetails.getZipCode(),
                vendorContext.getUpdatedZipCode(),
                "Updated zip code mismatch.");

        assertEquals(
                vendorDetails.getRegisteredUnder(),
                vendorContext.getUpdatedRegisteredUnder(),
                "Updated registered under mismatch.");

        assertEquals(
                vendorDetails.getServiceableArea(),
                vendorContext.getUpdatedServiceableArea(),
                "Updated serviceable area mismatch.");

        assertEquals(
                vendorDetails.getComments(),
                vendorContext.getUpdatedComments(),
                "Updated comments mismatch.");

        assertNotNull(
                vendorDetails.getVendorCode(),
                "Vendor code should not be null.");

        assertNotNull(
                vendorDetails.getUserId(),
                "User Id should not be null.");

        assertNotNull(
                vendorDetails.getDeliveryCenterId(),
                "Delivery Center should not be null.");
    }
}