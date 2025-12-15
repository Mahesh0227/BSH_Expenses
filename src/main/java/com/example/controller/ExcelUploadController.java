package com.example.controller;

import com.example.service.ExcelUploadService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ExcelUploadController {

    private final ExcelUploadService excelUploadService;

    public ExcelUploadController(ExcelUploadService excelUploadService) {
        this.excelUploadService = excelUploadService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadExcel(
            @RequestParam(value = "file", required = false) MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file received");
        }

        excelUploadService.processExcel(file);
        return ResponseEntity.ok("Excel uploaded successfully");
    }

}
