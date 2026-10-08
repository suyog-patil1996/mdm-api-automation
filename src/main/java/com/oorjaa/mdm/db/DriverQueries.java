package com.oorjaa.mdm.db;

import com.oorjaa.mdm.model.driver.DriverDetails;
import com.oorjaa.mdm.model.driver.DriverDocumentRow;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DriverQueries {

    private final DBConnection dbConnection;

    public DriverQueries(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private static final String DRIVER_SQL = """
            SELECT
                d.id AS driver_id,
                d.address1,
                d.city,
                d.country,
                d.user_status,
                d.user_id,
                d.vendor_id,
                d.state,
                d.created_by,
                d.created_date,
                d.updated_by,
                d.updated_date,
                u.id AS user_table_id,
                u.first_name,
                u.phone_number,
                u.status,
                u.keycloak_user_id,
                u.keycloak_username
            FROM lkart.user u
            INNER JOIN lkart.driver d ON u.id = d.user_id
            WHERE d.tenant_id = 1
              AND d.id = ?
            """;

    private static final String DOCUMENTS_SQL = """
            SELECT
                dd.document_name,
                dd.front_photo_id,
                dd.back_photo_id
            FROM lkart.document dd
            WHERE dd.entity_type = 'driver'
              AND dd.entity_id = ?
              AND dd.document_name IN (
                  'panCard',
                  'aadhaarCardNumber',
                  'policeVerification',
                  'licenseNumber'
              )
            ORDER BY dd.document_name
            """;

    public DriverDetails getDriverDetails(Integer driverId) {

        AllureHelper.attachSQL(DRIVER_SQL + " [driverId=" + driverId + "]");

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DRIVER_SQL)) {

            ps.setInt(1, driverId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                DriverDetails details = new DriverDetails();
                details.setDriverId(rs.getInt("driver_id"));
                details.setAddress1(rs.getString("address1"));
                details.setCity(rs.getString("city"));
                details.setCountry(rs.getString("country"));
                details.setUserStatus(rs.getString("user_status"));
                details.setUserId(rs.getObject("user_id") != null ? rs.getInt("user_id") : null);
                details.setVendorId(rs.getObject("vendor_id") != null ? rs.getInt("vendor_id") : null);
                details.setState(rs.getString("state"));
                details.setCreatedBy(rs.getObject("created_by") != null ? rs.getInt("created_by") : null);
                details.setCreatedDate(rs.getString("created_date"));
                details.setUpdatedBy(rs.getObject("updated_by") != null ? rs.getInt("updated_by") : null);
                details.setUpdatedDate(rs.getString("updated_date"));

                details.setFirstName(rs.getString("first_name"));
                details.setPhoneNumber(rs.getString("phone_number"));
                details.setStatus(rs.getString("status"));
                details.setKeycloakUserId(rs.getString("keycloak_user_id"));
                details.setKeycloakUsername(rs.getString("keycloak_username"));

                details.setDocuments(getDocuments(connection, driverId));
                return details;
            }
        } catch (Exception e) {
            AllureHelper.attachException(e);
            throw new RuntimeException("Failed to fetch driver. driverId=" + driverId, e);
        }
    }

    private List<DriverDocumentRow> getDocuments(Connection connection, Integer driverId)
            throws Exception {

        AllureHelper.attachSQL(DOCUMENTS_SQL + " [driverId=" + driverId + "]");
        List<DriverDocumentRow> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(DOCUMENTS_SQL)) {
            // entity_id may be string in DB
            ps.setString(1, String.valueOf(driverId));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DriverDocumentRow row = new DriverDocumentRow();
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
}