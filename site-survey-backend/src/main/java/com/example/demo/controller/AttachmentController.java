package com.example.demo.controller;

import com.example.demo.entity.Attachment;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.service.AttachmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentRepository attachmentRepository;

    public AttachmentController(
            AttachmentService attachmentService,
            AttachmentRepository attachmentRepository) {
        this.attachmentService = attachmentService;
        this.attachmentRepository = attachmentRepository;
    }

    @PostMapping("/space/{spaceId}")
    public ResponseEntity<String> uploadForSpace(
            @PathVariable("spaceId") Long spaceId,
            @RequestParam("file") MultipartFile file) throws Exception {

        attachmentService.uploadForSpace(spaceId, file);
        return ResponseEntity.ok("File uploaded successfully");
    }


    // ✅ Get attachments for a space
    @GetMapping("/space/{spaceId}")
    public List<Attachment> getBySpace(@PathVariable("spaceId") Long spaceId) {
        return attachmentRepository.findBySpaceId(spaceId);
    }
}
