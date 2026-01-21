package com.example.demo.service;

import com.example.demo.entity.Attachment;
import com.example.demo.entity.Space;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.SpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final SpaceRepository spaceRepository;

    // Absolute upload directory (project root/uploads)
    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads";

    public AttachmentService(
            AttachmentRepository attachmentRepository,
            SpaceRepository spaceRepository) {
        this.attachmentRepository = attachmentRepository;
        this.spaceRepository = spaceRepository;
    }

    public Attachment uploadForSpace(Long spaceId, MultipartFile file) throws IOException {

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        // Create uploads directory if not exists
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // ✅ CORRECT file path
        String filePath = dir.getAbsolutePath()
                + File.separator
                + file.getOriginalFilename();

        // Save file to disk
        file.transferTo(new File(filePath));

        // Save attachment metadata
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFilePath(filePath);
        attachment.setFileType(file.getContentType());
        attachment.setSpace(space);

        return attachmentRepository.save(attachment);
    }
}
