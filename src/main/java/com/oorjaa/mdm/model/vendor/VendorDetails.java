package com.oorjaa.mdm.model.vendor;

import java.sql.Timestamp;

public class VendorDetails {

    // Vendor Table
    private String nameOfCompany;
    private String ownerPhoneNumber;
    private String ownerName;
    private String city;
    private String country;
    private String address1;
    private String registeredUnder;
    private String userStatus;
    private String vendorCode;
    private String zipCode;
    private String serviceableArea;
    private String comments;
    private Integer userId;

    private String createdBy;
    private Timestamp createdDate;
    private String approvedStatus;
    private String updatedBy;
    private Timestamp updatedDate;

    // User Table
    private Integer dbUserId;
    private String firstName;
    private String phoneNumber;
    private String status;
    private String keycloakId;
    private String keycloakUsername;

    // Vendor Delivery Center
    private Integer deliveryCenterId;

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getRegisteredUnder() {
        return registeredUnder;
    }

    public void setRegisteredUnder(String registeredUnder) {
        this.registeredUnder = registeredUnder;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getServiceableArea() {
        return serviceableArea;
    }

    public void setServiceableArea(String serviceableArea) {
        this.serviceableArea = serviceableArea;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getDbUserId() {
        return dbUserId;
    }

    public void setDbUserId(Integer dbUserId) {
        this.dbUserId = dbUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getKeycloakUsername() {
        return keycloakUsername;
    }

    public void setKeycloakUsername(String keycloakUsername) {
        this.keycloakUsername = keycloakUsername;
    }

    public Integer getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(Integer deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }
}