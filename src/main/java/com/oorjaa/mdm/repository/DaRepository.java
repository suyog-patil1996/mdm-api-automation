package com.oorjaa.mdm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.db.DaQueries;
import com.oorjaa.mdm.model.da.DaDetails;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Repository;

@Repository
public class DaRepository {

    private final DaQueries daQueries;
    private final ObjectMapper objectMapper;

    public DaRepository(DaQueries daQueries,
                        ObjectMapper objectMapper) {
        this.daQueries = daQueries;
        this.objectMapper = objectMapper;
    }

    public DaDetails getDaDetails(Integer daId) {

        AllureHelper.addStep("Fetch DA Details From Database");

        DaDetails details = daQueries.getDaDetails(daId);

        if (details != null) {
            try {
                String json = objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(details);
                AllureHelper.attachJson("DA Database Record", json);
            } catch (Exception e) {
                AllureHelper.attachException(e);
            }
        }

        return details;
    }

    public boolean daExists(Integer daId) {
        return getDaDetails(daId) != null;
    }
}