package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Property;
import com.example.demo.repository.PropertyRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // ✅ GET ALL
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Property getById(@PathVariable Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    // ✅ CREATE
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyRepository.save(property);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id,
                                   @RequestBody Property updated) {
        return propertyRepository.findById(id)
                .map(p -> {
                    p.setPropertyName(updated.getPropertyName());
                    p.setAddress(updated.getAddress());
                    p.setCity(updated.getCity());
                    return propertyRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        propertyRepository.deleteById(id);
        return ResponseEntity.ok("Property deleted");
    }
}
