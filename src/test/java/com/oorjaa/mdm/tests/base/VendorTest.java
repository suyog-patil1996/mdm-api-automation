package com.oorjaa.mdm.tests.vendor;

import com.oorjaa.mdm.service.VendorService;
import com.oorjaa.mdm.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("MDM")
@Feature("Vendor Management")
@Owner("Suyog")
public class VendorTest extends BaseTest {

    private VendorService vendorService;

    @BeforeClass(alwaysRun = true)
    public void setup() {

        vendorService =
                context.getBean(VendorService.class);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify vendor can be created successfully.")
    public void createVendor() {

        vendorService.createVendor();
    }

    @Test(dependsOnMethods = "createVendor")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify duplicate phone number validation.")
    public void validateDuplicatePhoneNumber() {

        vendorService.validateDuplicatePhoneNumber();
    }

    @Test(dependsOnMethods = "validateDuplicatePhoneNumber")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify vendor approval.")
    public void approveVendor() {

        vendorService.approveVendor();
    }

    @Test(dependsOnMethods = "approveVendor")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify vendor search.")
    public void searchVendor() {

        vendorService.searchVendor();
    }

    @Test(dependsOnMethods = "searchVendor")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify vendor update.")
    public void updateVendor() {

        vendorService.updateVendor();
    }
}