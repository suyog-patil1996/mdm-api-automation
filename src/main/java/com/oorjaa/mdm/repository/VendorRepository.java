package com.oorjaa.mdm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oorjaa.mdm.db.VendorQueries;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Repository;

@Repository
public class VendorRepository {

    private final VendorQueries vendorQueries;
    private final ObjectMapper objectMapper;

    public VendorRepository(VendorQueries vendorQueries,
                            ObjectMapper objectMapper) {

        this.vendorQueries = vendorQueries;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns Vendor details from database.
     *
     * @param vendorId Vendor Id
     * @return VendorDetails
     */
    public VendorDetails getVendorDetails(Integer vendorId) {

        AllureHelper.addStep(
                "Fetch Vendor Details From Database");

        VendorDetails vendorDetails =
                vendorQueries.getVendorDetails(vendorId);

        if (vendorDetails != null) {

            try {

                String json =
                        objectMapper
                                .writerWithDefaultPrettyPrinter()
                                .writeValueAsString(vendorDetails);

                AllureHelper.attachJson(
                        "Vendor Database Record",
                        json);

            } catch (Exception e) {

                AllureHelper.attachException(e);
            }
        }

        return vendorDetails;
    }

    /**
     * Checks whether the vendor exists.
     *
     * @param vendorId Vendor Id
     * @return true if vendor exists
     */
    public boolean vendorExists(Integer vendorId) {

        return getVendorDetails(vendorId) != null;
    }

}
