package com.oorjaa.mdm.model.vendor;

public class VendorDocumentRow {

    private String documentName;
    private Integer frontPhotoId;
    private Integer backPhotoId;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getFrontPhotoId() {
        return frontPhotoId;
    }

    public void setFrontPhotoId(Integer frontPhotoId) {
        this.frontPhotoId = frontPhotoId;
    }

    public Integer getBackPhotoId() {
        return backPhotoId;
    }

    public void setBackPhotoId(Integer backPhotoId) {
        this.backPhotoId = backPhotoId;
    }
}