package com.oorjaa.mdm.db;

import com.oorjaa.mdm.model.vendor.VendorDetails;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class VendorQueries {

    private final DBConnection dbConnection;

    public VendorQueries(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public VendorDetails getVendorDetails(Integer vendorId) {

        String sql = """
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
                    u.id,
                    u.first_name,
                    u.phone_number,
                    u.status,
                    u.keycloak_user_id,
                    u.keycloak_username,
                    vdc.delivery_centers_id
                FROM lkart.user u
                INNER JOIN lkart.vendor v
                    ON u.id = v.user_id
                INNER JOIN lkart.vendor_delivery_centers vdc
                    ON vdc.vendor_id = v.id
                WHERE v.id = ?
                """;

        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, vendorId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                VendorDetails vendor = new VendorDetails();

                vendor.setCreatedBy(rs.getString("created_by"));
                vendor.setCreatedDate(rs.getTimestamp("created_date"));

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

                vendor.setUserId(rs.getInt("user_id"));

                vendor.setServiceableArea(rs.getString("serviceable_area"));
                vendor.setComments(rs.getString("comments"));

                vendor.setUpdatedBy(rs.getString("updated_by"));
                vendor.setUpdatedDate(rs.getTimestamp("updated_date"));

                vendor.setDbUserId(rs.getInt("id"));
                vendor.setFirstName(rs.getString("first_name"));
                vendor.setPhoneNumber(rs.getString("phone_number"));
                vendor.setStatus(rs.getString("status"));
                vendor.setKeycloakId(rs.getString("keycloak_user_id"));
                vendor.setKeycloakUsername(rs.getString("keycloak_username"));

                vendor.setDeliveryCenterId(rs.getInt("delivery_centers_id"));

                return vendor;
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to fetch Vendor details from database.",
                    e);
        }
    }
}