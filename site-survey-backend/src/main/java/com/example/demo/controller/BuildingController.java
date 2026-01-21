package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Building;
import com.example.demo.entity.Property;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.PropertyRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    private final BuildingRepository buildingRepository;
    private final PropertyRepository propertyRepository;

    public BuildingController(BuildingRepository buildingRepository,
                              PropertyRepository propertyRepository) {
        this.buildingRepository = buildingRepository;
        this.propertyRepository = propertyRepository;
    }

    // ✅ GET ALL
    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Building getById(@PathVariable Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found"));
    }

    // ✅ GET BY PROPERTY
    @GetMapping("/property/{propertyId}")
    public List<Building> getByProperty(
            @PathVariable("propertyId") Long propertyId) {

        return buildingRepository.findByPropertyId(propertyId);
    }


    // ✅ CREATE (linked to property)
    @PostMapping("/property/{propertyId}")
    public Building createBuilding(@PathVariable Long propertyId,
                                   @RequestBody Building building) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        building.setProperty(property);
        return buildingRepository.save(building);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Building updateBuilding(@PathVariable Long id,
                                   @RequestBody Building updated) {

        return buildingRepository.findById(id)
                .map(b -> {
                    b.setBuildingName(updated.getBuildingName());
                    return buildingRepository.save(b);
                })
                .orElseThrow(() -> new RuntimeException("Building not found"));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuilding(@PathVariable Long id) {
        buildingRepository.deleteById(id);
        return ResponseEntity.ok("Building deleted");
    }
}
