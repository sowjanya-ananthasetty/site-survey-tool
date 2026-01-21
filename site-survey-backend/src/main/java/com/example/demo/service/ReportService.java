package com.example.demo.service;

import com.example.demo.entity.ChecklistResponse;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

@Service
public class ReportService {

    public byte[] generateChecklistReport(ChecklistResponse response) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Checklist Report"));
        document.add(new Paragraph("Template: " + response.getTemplate().getName()));
        document.add(new Paragraph("Target Type: " + response.getTargetType()));
        document.add(new Paragraph("Target ID: " + response.getTargetId()));
        document.add(new Paragraph("Answers: " + response.getAnswersJson()));
        document.add(new Paragraph("Submitted At: " + response.getSubmittedAt()));

        document.close();
        return out.toByteArray();
    }
}
