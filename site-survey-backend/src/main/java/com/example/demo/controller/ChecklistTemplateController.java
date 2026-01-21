package com.example.demo.controller;

import com.example.demo.entity.ChecklistTemplate;
import com.example.demo.service.ChecklistTemplateService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/checklists/templates")
public class ChecklistTemplateController {

    private final ChecklistTemplateService service;

    public ChecklistTemplateController(ChecklistTemplateService service) {
        this.service = service;
    }

    @PostMapping
    public ChecklistTemplate create(@RequestBody ChecklistTemplate template) {
        return service.create(template);
    }

    @GetMapping
    public List<ChecklistTemplate> getAll() {
        return service.getAll();
    }
}
