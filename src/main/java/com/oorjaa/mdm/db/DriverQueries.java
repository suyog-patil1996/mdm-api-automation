package com.oorjaa.mdm.db;

import com.oorjaa.mdm.model.driver.DriverDetails;
import com.oorjaa.mdm.utils.AllureHelper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class DriverQueries {

    private static final String GET_DRIVER_BY_ID = """
            SELECT
                u.id,
                u.first_name,
                u.last_name,
                u.phone_number,
                u.keycloak_user_id,
                u.keycloak_username,
                d.id AS driver_id,
                d.state,
                d.city,
                d.created_by,
                d.created_date,
                d.updated_by,
                d.updated_date,
                d.address1,
                d.vendor_id
            FROM lkart.user u
            INNER JOIN lkart.driver d ON u.id = d.user_id
            WHERE d.tenant_id = 1
              AND d.id = ?
            """;

    private final DBConnection dbConnection;

    public DriverQueries(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DriverDetails getDriverDetails(Integer driverId) {

        AllureHelper.attachSQL(GET_DRIVER_BY_ID + " [driverId=" + driverId + "]");

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_DRIVER_BY_ID)) {

            ps.setInt(1, driverId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                DriverDetails details = new DriverDetails();
                details.setUserId(rs.getInt("id"));
                details.setFirstName(rs.getString("first_name"));
                details.setLastName(rs.getString("last_name"));
                details.setPhoneNumber(rs.getString("phone_number"));
                details.setKeycloakUserId(rs.getString("keycloak_user_id"));
                details.setKeycloakUsername(rs.getString("keycloak_username"));
                details.setDriverId(rs.getInt("driver_id"));
                details.setState(rs.getString("state"));
                details.setCity(rs.getString("city"));
                details.setCreatedBy(
                        rs.getObject("created_by") != null ? rs.getInt("created_by") : null);
                details.setCreatedDate(rs.getString("created_date"));
                details.setUpdatedBy(
                        rs.getObject("updated_by") != null ? rs.getInt("updated_by") : null);
                details.setUpdatedDate(rs.getString("updated_date"));
                details.setAddress1(rs.getString("address1"));
                details.setVendorId(
                        rs.getObject("vendor_id") != null ? rs.getInt("vendor_id") : null);

                return details;
            }

        } catch (Exception e) {
            AllureHelper.attachException(e);
            throw new RuntimeException(
                    "Failed to fetch driver from DB. driverId=" + driverId, e);
        }
    }
}