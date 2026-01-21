package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GeometryUpdateRequest;
import com.example.demo.entity.Floor;
import com.example.demo.entity.Space;
import com.example.demo.repository.FloorRepository;
import com.example.demo.repository.SpaceRepository;
import com.example.demo.service.SpaceImportService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceRepository spaceRepository;
    private final FloorRepository floorRepository;
    private final SpaceImportService spaceImportService;

    public SpaceController(
            SpaceRepository spaceRepository,
            FloorRepository floorRepository,
            SpaceImportService spaceImportService) {
        this.spaceRepository = spaceRepository;
        this.floorRepository = floorRepository;
        this.spaceImportService = spaceImportService;
    }

    // ✅ GET ALL SPACES
    @GetMapping
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    // ✅ CREATE SPACE UNDER FLOOR
    @PostMapping("/floor/{floorId}")
    public Space createSpace(
            @PathVariable Long floorId,
            @RequestBody Space space) {

        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new RuntimeException("Floor not found"));

        space.setFloor(floor);
        return spaceRepository.save(space);
    }

    @PutMapping("/{id}/geometry")
    @Transactional
    public ResponseEntity<String> updateSpaceGeometry(
            @PathVariable("id") Long id,
            @RequestBody GeometryUpdateRequest request) {

        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        space.setGeometryType(request.getGeometryType());
        space.setGeometryData(request.getGeometryData());

        spaceRepository.save(space);

        return ResponseEntity.ok("Geometry updated successfully");
    }






    // ✅ DELETE SPACE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable Long id) {

        if (!spaceRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Space not found");
        }

        spaceRepository.deleteById(id);
        return ResponseEntity.ok("Space deleted successfully");
    }

    // ✅ CSV UPLOAD
    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadCSV(
            @RequestParam("file") MultipartFile file) {

        try {
            spaceImportService.importSpaces(file);
            return ResponseEntity.ok("CSV uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Upload failed: " + e.getMessage());
        }
    }
}
