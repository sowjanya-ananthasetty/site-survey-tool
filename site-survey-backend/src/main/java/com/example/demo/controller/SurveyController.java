package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Survey;
import com.example.demo.repository.SurveyRepository;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyRepository surveyRepository;

    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    // ✅ GET ALL SURVEYS
    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // ✅ CREATE SURVEY
    @PostMapping
    public Survey saveSurvey(@RequestBody Survey survey) {
        return surveyRepository.save(survey);
    }

    // ✅ DELETE SURVEY
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurvey(@PathVariable("id") Long id) {

        if (!surveyRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Survey not found");
        }

        surveyRepository.deleteById(id);
        return ResponseEntity.ok("Survey deleted successfully");
    }

    // ✅ UPDATE SURVEY (FIXED)
    @PutMapping("/{id}")
    public Survey updateSurvey(@PathVariable("id") Long id,
                               @RequestBody Survey updatedSurvey) {

        return surveyRepository.findById(id)
                .map(existingSurvey -> {
                    existingSurvey.setName(updatedSurvey.getName());
                    existingSurvey.setEmail(updatedSurvey.getEmail());
                    existingSurvey.setLocation(updatedSurvey.getLocation());
                    return surveyRepository.save(existingSurvey);
                })
                .orElseThrow(() ->
                        new RuntimeException("Survey not found with id " + id));
    }
}
