package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Building;
import com.example.demo.entity.Floor;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.FloorRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    public FloorController(FloorRepository floorRepository,
                           BuildingRepository buildingRepository) {
        this.floorRepository = floorRepository;
        this.buildingRepository = buildingRepository;
    }

    /* =========================
       GET ALL FLOORS
    ========================= */
    @GetMapping
    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    /* =========================
       GET FLOORS BY BUILDING
    ========================= */
    @GetMapping("/building/{buildingId}")
    public List<Floor> getByBuilding(
            @PathVariable("buildingId") Long buildingId) {

        return floorRepository.findByBuildingId(buildingId);
    }



    /* =========================
       GET FLOOR BY ID
    ========================= */
    @GetMapping("/{id}")
    public Floor getById(@PathVariable Long id) {
        return floorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floor not found"));
    }

    /* =========================
       CREATE FLOOR
    ========================= */
    @PostMapping("/building/{buildingId}")
    public Floor createFloor(@PathVariable Long buildingId,
                             @RequestBody Floor floor) {

        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        floor.setBuilding(building);
        return floorRepository.save(floor);
    }

    /* =========================
       UPDATE FLOOR
    ========================= */
    @PutMapping("/{id}")
    public Floor updateFloor(@PathVariable Long id,
                             @RequestBody Floor updated) {

        return floorRepository.findById(id)
                .map(f -> {
                    f.setFloorNumber(updated.getFloorNumber());
                    return floorRepository.save(f);
                })
                .orElseThrow(() -> new RuntimeException("Floor not found"));
    }

    /* =========================
       DELETE FLOOR
    ========================= */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFloor(@PathVariable Long id) {
        floorRepository.deleteById(id);
        return ResponseEntity.ok("Floor deleted");
    }

    /* =========================
       UPLOAD FLOOR PLAN
    ========================= */
    @PostMapping("/{id}/upload-plan")
    public ResponseEntity<String> uploadFloorPlan(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file) throws Exception {

        Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floor not found"));

        Path uploadDir = Paths.get("uploads/floorplans");
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve("floor_" + id + "_" + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        floor.setFloorPlanPath(filePath.toString());
        floorRepository.save(floor);

        return ResponseEntity.ok("Floor plan uploaded");
    }


    /* =========================
       VIEW FLOOR PLAN (PREVIEW)
    ========================= */
    @GetMapping("/{id}/plan")
    public ResponseEntity<?> viewFloorPlan(
            @PathVariable("id") Long id) {

        Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floor not found"));

        // 1️⃣ Check DB value
        if (floor.getFloorPlanPath() == null || floor.getFloorPlanPath().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("No floor plan uploaded for this floor");
        }

        try {
            Path path = Paths.get(floor.getFloorPlanPath());

            // 2️⃣ Check file existence
            if (!Files.exists(path)) {
                return ResponseEntity
                        .badRequest()
                        .body("Floor plan file missing on server");
            }

            Resource resource = new UrlResource(path.toUri());

            // 3️⃣ Return file safely
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // IMPORTANT for debugging
            return ResponseEntity
                    .internalServerError()
                    .body("Error loading floor plan");
        }
    }

}
