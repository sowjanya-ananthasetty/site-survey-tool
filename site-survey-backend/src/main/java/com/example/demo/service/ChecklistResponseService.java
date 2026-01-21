package com.example.demo.service;

import com.example.demo.entity.ChecklistResponse;
import com.example.demo.entity.ChecklistTemplate;
import com.example.demo.dto.SurveySummaryDTO;
import com.example.demo.repository.ChecklistResponseRepository;
import com.example.demo.repository.ChecklistTemplateRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ChecklistResponseService {

    private final ChecklistResponseRepository responseRepository;
    private final ChecklistTemplateRepository templateRepository;

    public ChecklistResponseService(
            ChecklistResponseRepository responseRepository,
            ChecklistTemplateRepository templateRepository) {
        this.responseRepository = responseRepository;
        this.templateRepository = templateRepository;
    }

    /* =========================
       SAVE DRAFT
    ========================= */
    public void saveDraft(ChecklistResponse response) {

        Long templateId = response.getTemplate().getId();

        ChecklistTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("ChecklistTemplate not found"));

        response.setTemplate(template);

        // NEW: Always mark as DRAFT
        response.setStatus("DRAFT");

        if (response.getId() != null) {
            ChecklistResponse existing =
                    responseRepository.findById(response.getId())
                            .orElseThrow(() -> new RuntimeException("Response not found"));

            if (existing.getSubmittedAt() != null) {
                throw new RuntimeException("Cannot edit a submitted response");
            }
        }

        responseRepository.save(response);
    }

    /* =========================
       SUBMIT FINAL
    ========================= */
    public void submit(Long id) {

        ChecklistResponse response =
                responseRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Checklist response not found"));

        if (response.getSubmittedAt() != null) {
        	throw new IllegalStateException("Checklist already submitted");

        }

        // NEW: Mark as SUBMITTED
        response.setStatus("SUBMITTED");
        response.setSubmittedAt(LocalDateTime.now());

        responseRepository.save(response);
    }

    /* =========================
       DASHBOARD SUMMARY
    ========================= */
    public SurveySummaryDTO getSurveySummary() {

        SurveySummaryDTO dto = new SurveySummaryDTO();

        long total = responseRepository.count();
        long submitted = responseRepository.countBySubmittedAtIsNotNull();
        long draft = responseRepository.countBySubmittedAtIsNull();

        dto.setTotal(total);
        dto.setSubmitted(submitted);
        dto.setDraft(draft);

        return dto;
    }
}
