package com.example.demo.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "floor")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Floor number (1, 2, 3, ...)
    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    // ✅ NEW: Floor plan file path (PDF / Image)
    @Column(name = "floor_plan_path")
    private String floorPlanPath;

    // Many floors belong to one building
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonIgnoreProperties({"floors", "property"})
    private Building building;

    // -------- Getters & Setters --------

    public Long getId() {
        return id;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getFloorPlanPath() {
        return floorPlanPath;
    }

    public void setFloorPlanPath(String floorPlanPath) {
        this.floorPlanPath = floorPlanPath;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
