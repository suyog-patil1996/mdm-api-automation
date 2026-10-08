package com.oorjaa.mdm.db;

import com.oorjaa.mdm.model.vendor.VendorBankRow;
import com.oorjaa.mdm.model.vendor.VendorDetails;
import com.oorjaa.mdm.model.vendor.VendorDocumentRow;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendorQueries {

    private final DBConnection dbConnection;

    public VendorQueries(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private static final String VENDOR_SQL = """
            SELECT
                v.id,
                v.address1,
                v.city,
                v.country,
                v.name_of_company,
                v.owner_name,
                v.owner_phone_number,
                v.registered_under,
                v.user_status,
                v.unique_code,
                v.zip_code,
                v.user_id,
                v.serviceable_area,
                v.comments,
                v.created_by,
                v.created_date,
                v.updated_by,
                v.updated_date,
                u.id AS user_table_id,
                u.first_name,
                u.phone_number,
                u.status,
                u.keycloak_user_id,
                u.keycloak_username,
                vdc.delivery_centers_id
            FROM lkart.user u
            INNER JOIN lkart.vendor v ON u.id = v.user_id
            INNER JOIN lkart.vendor_delivery_centers vdc ON vdc.vendor_id = v.id
            WHERE v.tenant_id = 1
              AND v.id = ?
            """;

    private static final String DOCUMENTS_SQL = """
            SELECT
                d.document_name,
                d.front_photo_id,
                d.back_photo_id
            FROM lkart.document d
            WHERE d.entity_type = 'vendor'
              AND d.entity_id = ?
              AND d.document_name IN (
                  'msmeNumber',
                  'cancelledCheque',
                  'gstNumber',
                  'aadharCardNumber',
                  'panNumber'
              )
            ORDER BY d.document_name
            """;

    // Latest bank row for this vendor
    private static final String BANK_SQL = """
            SELECT
                bd.account_number,
                bd.routing_code,
                bd.account_holder_name,
                bd.account_type,
                bd.linked_phone_number
            FROM lkart.bank_details bd
            WHERE bd.entity_id = ?
            ORDER BY bd.id DESC
            LIMIT 1
            """;

    public VendorDetails getVendorDetails(Integer vendorId) {

        AllureHelper.attachSQL(VENDOR_SQL + " [vendorId=" + vendorId + "]");

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(VENDOR_SQL)) {

            ps.setInt(1, vendorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                VendorDetails vendor = new VendorDetails();
                vendor.setVendorId(rs.getInt("id"));
                vendor.setAddress1(rs.getString("address1"));
                vendor.setCity(rs.getString("city"));
                vendor.setCountry(rs.getString("country"));
                vendor.setNameOfCompany(rs.getString("name_of_company"));
                vendor.setOwnerName(rs.getString("owner_name"));
                vendor.setOwnerPhoneNumber(rs.getString("owner_phone_number"));
                vendor.setRegisteredUnder(rs.getString("registered_under"));
                vendor.setUserStatus(rs.getString("user_status"));
                vendor.setVendorCode(rs.getString("unique_code"));
                vendor.setZipCode(rs.getString("zip_code"));
                vendor.setUserId(rs.getObject("user_id") != null ? rs.getInt("user_id") : null);
                vendor.setServiceableArea(rs.getString("serviceable_area"));
                vendor.setComments(rs.getString("comments"));
                vendor.setCreatedBy(rs.getString("created_by"));
                vendor.setCreatedDate(rs.getTimestamp("created_date"));
                vendor.setUpdatedBy(rs.getString("updated_by"));
                vendor.setUpdatedDate(rs.getTimestamp("updated_date"));

                vendor.setDbUserId(rs.getInt("user_table_id"));
                vendor.setFirstName(rs.getString("first_name"));
                vendor.setPhoneNumber(rs.getString("phone_number"));
                vendor.setStatus(rs.getString("status"));
                vendor.setKeycloakId(rs.getString("keycloak_user_id"));
                vendor.setKeycloakUsername(rs.getString("keycloak_username"));
                vendor.setDeliveryCenterId(
                        rs.getObject("delivery_centers_id") != null
                                ? rs.getInt("delivery_centers_id") : null);

                vendor.setDocuments(getDocuments(connection, vendorId));
                vendor.setBankDetails(getLatestBank(connection, vendorId));

                return vendor;
            }
        } catch (Exception e) {
            AllureHelper.attachException(e);
            throw new RuntimeException("Failed to fetch vendor. vendorId=" + vendorId, e);
        }
    }

    private List<VendorDocumentRow> getDocuments(Connection connection, Integer vendorId)
            throws Exception {

        AllureHelper.attachSQL(DOCUMENTS_SQL + " [vendorId=" + vendorId + "]");

        List<VendorDocumentRow> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(DOCUMENTS_SQL)) {
            ps.setString(1, String.valueOf(vendorId)); // entity_id often stored as string

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VendorDocumentRow row = new VendorDocumentRow();
                    row.setDocumentName(rs.getString("document_name"));
                    row.setFrontPhotoId(
                            rs.getObject("front_photo_id") != null
                                    ? rs.getInt("front_photo_id") : null);
                    row.setBackPhotoId(
                            rs.getObject("back_photo_id") != null
                                    ? rs.getInt("back_photo_id") : null);
                    list.add(row);
                }
            }
        }
        return list;
    }

    private VendorBankRow getLatestBank(Connection connection, Integer vendorId)
            throws Exception {

        AllureHelper.attachSQL(BANK_SQL + " [vendorId=" + vendorId + "]");

        try (PreparedStatement ps = connection.prepareStatement(BANK_SQL)) {
            // entity_id may be string or int depending on schema – try int first
            ps.setString(1, String.valueOf(vendorId));

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                VendorBankRow bank = new VendorBankRow();
                bank.setAccountNumber(rs.getString("account_number"));
                bank.setRoutingCode(rs.getString("routing_code"));
                bank.setAccountHolderName(rs.getString("account_holder_name"));
                bank.setAccountType(rs.getString("account_type"));
                bank.setLinkedPhoneNumber(rs.getString("linked_phone_number"));
                return bank;
            }
        }
    }
}