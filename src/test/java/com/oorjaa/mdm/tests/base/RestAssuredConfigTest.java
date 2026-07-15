package com.oorjaa.mdm.tests.base;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class RestAssuredConfigTest extends BaseTest {

    @Test
    public void verifyConfiguration() {

        System.out.println(RestAssured.baseURI);

    }

}