package com.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
public class TemplateController {

    @GetMapping("/api/template/download")
    public ResponseEntity<ByteArrayResource> downloadTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {

            /* ================= SHEET 1 : INSTRUCTIONS ================= */
            Sheet instructions = workbook.createSheet("Instructions");
            instructions.setColumnWidth(0, 9000);

            String[] notes = {
                "IMPORTANT INSTRUCTIONS",
                "",
                "1. Do NOT change column names",
                "2. Dates must be in YYYY-MM-DD format",
                "3. Amount must be numeric (no ₹ symbol)",
                "",
                "INCOME SHEET FORMAT:",
                "source | date | amount",
                "Example: Salary | 2025-12-01 | 25000",
                "",
                "EXPENSES SHEET FORMAT:",
                "expenseDetails | date | payment | amount",
                "Example: Rent | 2025-12-02 | Cash | 8000",
                "",
                "SAVINGS SHEET FORMAT:",
                "details | date | amount",
                "Example: Fixed Deposit | 2025-12-05 | 3000"
            };

            for (int i = 0; i < notes.length; i++) {
                Row row = instructions.createRow(i);
                row.createCell(0).setCellValue(notes[i]);
            }

            /* ================= SHEET 2 : INCOME ================= */
            Sheet income = workbook.createSheet("Income");
            Row iHeader = income.createRow(0);
            iHeader.createCell(0).setCellValue("source");
            iHeader.createCell(1).setCellValue("date");
            iHeader.createCell(2).setCellValue("amount");

            Row iSample = income.createRow(1);
            iSample.createCell(0).setCellValue("Salary");
            iSample.createCell(1).setCellValue("2025-12-01");
            iSample.createCell(2).setCellValue(25000);

            /* ================= SHEET 3 : EXPENSES ================= */
            Sheet expenses = workbook.createSheet("Expenses");
            Row eHeader = expenses.createRow(0);
            eHeader.createCell(0).setCellValue("expenseDetails");
            eHeader.createCell(1).setCellValue("date");
            eHeader.createCell(2).setCellValue("payment");
            eHeader.createCell(3).setCellValue("amount");

            Row eSample = expenses.createRow(1);
            eSample.createCell(0).setCellValue("Groceries");
            eSample.createCell(1).setCellValue("2025-12-02");
            eSample.createCell(2).setCellValue("UPI");
            eSample.createCell(3).setCellValue(1500);

            /* ================= SHEET 4 : SAVINGS ================= */
            Sheet savings = workbook.createSheet("Savings");
            Row sHeader = savings.createRow(0);
            sHeader.createCell(0).setCellValue("details");
            sHeader.createCell(1).setCellValue("date");
            sHeader.createCell(2).setCellValue("amount");

            Row sSample = savings.createRow(1);
            sSample.createCell(0).setCellValue("Emergency Fund");
            sSample.createCell(1).setCellValue("2025-12-05");
            sSample.createCell(2).setCellValue(3000);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=BSH_Financial_Data_Template.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(out.toByteArray()));

        } catch (Exception e) {
            throw new RuntimeException("Template generation failed", e);
        }
    }
}
