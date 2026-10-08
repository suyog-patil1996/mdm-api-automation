package com.oorjaa.mdm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.db.DriverQueries;
import com.oorjaa.mdm.model.driver.DriverDetails;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Repository;

@Repository
public class DriverRepository {

    private final DriverQueries driverQueries;
    private final ObjectMapper objectMapper;

    public DriverRepository(DriverQueries driverQueries,
                            ObjectMapper objectMapper) {
        this.driverQueries = driverQueries;
        this.objectMapper = objectMapper;
    }

    public DriverDetails getDriverDetails(Integer driverId) {

        AllureHelper.addStep("Fetch Driver Details From Database");

        DriverDetails details = driverQueries.getDriverDetails(driverId);

        if (details != null) {
            try {
                String json = objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(details);
                AllureHelper.attachJson("Driver Database Record", json);
            } catch (Exception e) {
                AllureHelper.attachException(e);
            }
        }

        return details;
    }

    public boolean driverExists(Integer driverId) {
        return getDriverDetails(driverId) != null;
    }
}