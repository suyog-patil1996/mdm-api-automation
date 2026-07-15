package com.oorjaa.mdm.tests.base;

import com.oorjaa.mdm.service.LoginService;
import com.oorjaa.mdm.tests.base.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLogin() {

        LoginService service =
                context.getBean(LoginService.class);

        service.login();

    }

}