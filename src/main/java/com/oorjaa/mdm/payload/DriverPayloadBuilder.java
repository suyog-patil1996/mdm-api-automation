package com.oorjaa.mdm.payload;

import com.oorjaa.mdm.constants.DriverConstants;
import com.oorjaa.mdm.context.DriverContext;
import com.oorjaa.mdm.model.driver.request.*;
import com.oorjaa.mdm.utils.TestDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class DriverPayloadBuilder {

    private final DriverContext driverContext;
    private final Random random = new Random();

    public DriverPayloadBuilder(DriverContext driverContext) {
        this.driverContext = driverContext;
    }

    public CreateDriverRequest buildCreateDriverRequest() {

        String phone10 = TestDataGenerator.generatePhoneNumber();
        String phone = "+91" + phone10;
        String altPhone = String.valueOf(1000000000L + random.nextInt(900000000));
        String license = "MH12ETW" + (100000 + random.nextInt(900000));
        String firstName = "AUTO_DRV";
        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();

        driverContext.setVendorId(DriverConstants.DEFAULT_VENDOR_ID);
        driverContext.setFirstName(firstName);
        driverContext.setLastName("");
        driverContext.setPhoneNumber(phone);
        driverContext.setDrivingLicense(license);
        driverContext.setAddress1("Pune");
        driverContext.setCity(DriverConstants.CITY);
        driverContext.setState(DriverConstants.STATE);
        driverContext.setCountry(DriverConstants.COUNTRY);

        return CreateDriverRequest.builder()
                .id(null)
                .vendorId(DriverConstants.DEFAULT_VENDOR_ID)
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(license)
                .dateOfBirth(null)
                .firstName(firstName)
                .middleName(null)
                .lastName("")
                .phoneNumber(phone)
                .alternatePhoneNumber(altPhone)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId(DriverConstants.CITY_ID)
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city(DriverConstants.CITY)
                .address1("Pune")
                .address2("Baner")
                .address3("Baner gaon")
                .password(DriverConstants.DEFAULT_PASSWORD)
                .isLicenceVerified(false)
                .documents(buildDocuments(license, pan, aadhaar, "NOT_STARTED"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    public CreateDriverRequest buildDuplicateDriverRequest() {

        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();

        return CreateDriverRequest.builder()
                .id(null)
                .vendorId(driverContext.getVendorId())
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(driverContext.getDrivingLicense())
                .dateOfBirth(null)
                .firstName(driverContext.getFirstName())
                .middleName(null)
                .lastName(driverContext.getLastName() != null
                        ? driverContext.getLastName() : "")
                .phoneNumber(driverContext.getPhoneNumber())
                .alternatePhoneNumber(null)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId(DriverConstants.CITY_ID)
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city(DriverConstants.CITY)
                .address1(driverContext.getAddress1())
                .address2("Baner")
                .address3("Baner gaon")
                .password(DriverConstants.DEFAULT_PASSWORD)
                .isLicenceVerified(false)
                .documents(buildDocuments(
                        driverContext.getDrivingLicense(),
                        pan,
                        aadhaar,
                        "NOT_STARTED"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    public ApproveDriverRequest buildApproveDriverRequest() {
        return ApproveDriverRequest.builder()
                .id(driverContext.getDriverId())
                .userStatus("ACTIVE")
                .build();
    }

    public SearchDriverRequest buildSearchDriverRequest() {
        return SearchDriverRequest.builder()
                .limit(10)
                .pageId(0)
                .searchText(driverContext.getPhoneNumber())  // or firstName
                .build();
    }

    public UpdateDriverRequest buildUpdateDriverRequest() {

        String updatedFirstName = "AUTO_DRV_UPD";
        String updatedPhone10 = TestDataGenerator.generatePhoneNumber();
        String updatedPhone = "+91" + updatedPhone10;
        String altPhone = String.valueOf(1000000000L + random.nextInt(900000000));
        String pan = TestDataGenerator.generatePanNumber();
        String aadhaar = TestDataGenerator.generateAadharNumber();

        driverContext.setUpdatedFirstName(updatedFirstName);
        driverContext.setUpdatedAddress1("Mumbai");
        driverContext.setUpdatedCity("Mumbai");
        driverContext.setUpdatedPhoneNumber(updatedPhone);

        return UpdateDriverRequest.builder()
                .id(driverContext.getDriverId())
                .vendorId(driverContext.getVendorId())
                .vehicleId(null)
                .deliveryCenterId(null)
                .drivingLicense(driverContext.getDrivingLicense())
                .dateOfBirth(null)
                .firstName(updatedFirstName)
                .middleName(null)
                .lastName("")
                .phoneNumber(updatedPhone)
                .alternatePhoneNumber(altPhone)
                .vehicleClasses(Collections.emptyList())
                .countryId(DriverConstants.COUNTRY_ID)
                .stateId(DriverConstants.STATE_ID)
                .cityId(DriverConstants.MUMBAI_CITY_ID)
                .country(DriverConstants.COUNTRY)
                .state(DriverConstants.STATE)
                .city("Mumbai")
                .address1("Mumbai")
                .address2("Andheri")
                .address3("Mcdonalds")
                .isLicenceVerified(false)
                .documents(buildDocuments(
                        driverContext.getDrivingLicense(),
                        pan,
                        aadhaar,
                        "VERIFIED"))
                .vehicleRegistrationNumber(null)
                .isVehicleAssigned(false)
                .permanentDeliveryCenterId(null)
                .dcAssigned(false)
                .build();
    }

    private List<DriverDocument> buildDocuments(
            String license,
            String pan,
            String aadhaar,
            String policeStatus) {

        return Arrays.asList(
                DriverDocument.builder()
                        .documentCategory("DRIVING_LICENSE")
                        .documentNumber(license)
                        .countryCode("IN")
                        .documentName("licenseNumber")
                        .expiryDate("2026-12-31")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("PERSONAL_TAX")
                        .documentNumber(pan)
                        .countryCode("IN")
                        .documentName("panCard")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("NATIONAL_ID")
                        .documentNumber(aadhaar)
                        .countryCode("IN")
                        .documentName("aadhaarCardNumber")
                        .build(),
                DriverDocument.builder()
                        .documentCategory("POLICE_VERIFICATION")
                        .documentNumber("")
                        .countryCode("IN")
                        .documentName("policeVerification")
                        .issueDate("")
                        .expiryDate("")
                        .verificationStatus(policeStatus)
                        .build()
        );
    }
}