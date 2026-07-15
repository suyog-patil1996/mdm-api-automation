package com.oorjaa.mdm.config;

import com.oorjaa.mdm.utils.ConfigReader;
import io.restassured.RestAssured;
import org.springframework.stereotype.Component;

@Component
public class RestAssuredConfig {

    public RestAssuredConfig(ConfigReader configReader) {

        RestAssured.baseURI =
                configReader.get("baseUrl");

    }

}