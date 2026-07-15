package com.oorjaa.mdm.tests.base;

import com.oorjaa.mdm.service.VendorService;
import com.oorjaa.mdm.tests.base.BaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VendorTest extends BaseTest {

    private VendorService vendorService;

    @BeforeClass(alwaysRun = true)
    public void setup() {

        vendorService =
                context.getBean(VendorService.class);
    }

    @Test(priority = 1)
    @Description("Create Vendor")
    public void createVendor() {

        vendorService.createVendor();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "createVendor")
    @Description("Duplicate Phone Validation")
    public void validateDuplicatePhoneNumber() {

        vendorService.validateDuplicatePhoneNumber();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "validateDuplicatePhoneNumber")
    @Description("Approve Vendor")
    public void approveVendor() {

        vendorService.approveVendor();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "approveVendor")
    @Description("Search Vendor")
    public void searchVendor() {

        vendorService.searchVendor();
    }

    @Test(
            priority = 5,
            dependsOnMethods = "searchVendor")
    @Description("Update Vendor")
    public void updateVendor() {

        vendorService.updateVendor();
    }
}