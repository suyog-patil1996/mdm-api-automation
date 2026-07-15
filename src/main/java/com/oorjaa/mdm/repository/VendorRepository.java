package com.oorjaa.mdm.repository;

import com.oorjaa.mdm.db.VendorQueries;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import org.springframework.stereotype.Repository;

@Repository
public class VendorRepository {

    private final VendorQueries vendorQueries;

    public VendorRepository(VendorQueries vendorQueries) {
        this.vendorQueries = vendorQueries;
    }

    /**
     * Returns Vendor details from database.
     *
     * @param vendorId Vendor Id
     * @return VendorDetails
     */
    public VendorDetails getVendorDetails(Integer vendorId) {
        return vendorQueries.getVendorDetails(vendorId);
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