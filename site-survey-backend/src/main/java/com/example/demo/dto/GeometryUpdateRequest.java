package com.example.demo.dto;

public class GeometryUpdateRequest {

    private String geometryType;
    private String geometryData;

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public String getGeometryData() {
        return geometryData;
    }

    public void setGeometryData(String geometryData) {
        this.geometryData = geometryData;
    } 
}
