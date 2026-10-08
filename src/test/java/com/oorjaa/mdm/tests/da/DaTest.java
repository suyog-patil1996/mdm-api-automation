package com.oorjaa.mdm.tests.da;

import com.oorjaa.mdm.service.DaService;
import com.oorjaa.mdm.service.LoginService;
import com.oorjaa.mdm.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("MDM")
@Feature("Driver Assistant Management")
@Owner("Suyog")
public class DaTest extends BaseTest {

    private DaService daService;
    private LoginService loginService;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        daService = context.getBean(DaService.class);
        loginService = context.getBean(LoginService.class);
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    public void login() {
        loginService.login();
    }

    @Test(priority = 2, dependsOnMethods = "login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create DA")
    public void createDa() {
        daService.createDa();
    }

    @Test(priority = 3, dependsOnMethods = "createDa")
    @Severity(SeverityLevel.NORMAL)
    @Story("Duplicate DA")
    public void validateDuplicatePhoneNumber() {
        daService.validateDuplicatePhoneNumber();
    }

    @Test(priority = 4, dependsOnMethods = "validateDuplicatePhoneNumber")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Approve DA")
    public void approveDa() {
        daService.approveDa();
    }

    @Test(priority = 5, dependsOnMethods = "approveDa")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search DA")
    public void searchDa() {
        daService.searchDa();
    }

    @Test(priority = 6, dependsOnMethods = "searchDa")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Update DA")
    public void updateDa() {
        daService.updateDa();
    }
}