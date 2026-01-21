package com.example.demo.service;

import com.example.demo.entity.ChecklistTemplate;
import com.example.demo.repository.ChecklistTemplateRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChecklistTemplateService {

    private final ChecklistTemplateRepository repository;

    public ChecklistTemplateService(ChecklistTemplateRepository repository) {
        this.repository = repository;
    }

    public ChecklistTemplate create(ChecklistTemplate template) {
        return repository.save(template);
    }

    public List<ChecklistTemplate> getAll() {
        return repository.findAll();
    }
}
