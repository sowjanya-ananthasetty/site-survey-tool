package com.example.demo.controller;

import com.example.demo.dto.SurveySummaryDTO;
import com.example.demo.entity.ChecklistResponse;
import com.example.demo.repository.ChecklistResponseRepository;
import com.example.demo.service.ChecklistResponseService;
import com.example.demo.service.ReportService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/checklists/responses")
@CrossOrigin(origins = "*")
public class ChecklistResponseController {

    private final ChecklistResponseService service;
    private final ChecklistResponseRepository responseRepository;
    private final ReportService reportService;

    public ChecklistResponseController(
            ChecklistResponseService service,
            ChecklistResponseRepository responseRepository,
            ReportService reportService) {

        this.service = service;
        this.responseRepository = responseRepository;
        this.reportService = reportService;
    }

    /* =========================
       SAVE DRAFT
    ========================= */
    @PostMapping
    public ResponseEntity<String> saveDraft(
            @RequestBody ChecklistResponse response) {

        service.saveDraft(response);
        return ResponseEntity.ok("Draft saved successfully");
    }

    /* =========================
       SUBMIT CHECKLIST
       (CLEAN MESSAGE HANDLING)
    ========================= */
    @PostMapping("/{id}/submit")
    public ResponseEntity<String> submit(@PathVariable("id") Long id) {
        try {
            service.submit(id);
            return ResponseEntity.ok("Checklist submitted successfully");
        } catch (IllegalStateException e) {
            // ✅ Clean business message (NO 500)
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    /* =========================
       CHECKLIST HISTORY
    ========================= */
    @GetMapping
    public ResponseEntity<List<ChecklistResponse>> getAllResponses() {
        return ResponseEntity.ok(responseRepository.findAll());
    }

    /* =========================
       DASHBOARD SUMMARY
    ========================= */
    @GetMapping("/summary")
    public SurveySummaryDTO summary() {
        return service.getSurveySummary();
    }

    /* =========================
       DOWNLOAD PDF REPORT
    ========================= */
    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> downloadReport(
            @PathVariable("id") Long id) {

        ChecklistResponse response =
                responseRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Checklist not found"));

        byte[] pdf = reportService.generateChecklistReport(response);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=checklist-report.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
