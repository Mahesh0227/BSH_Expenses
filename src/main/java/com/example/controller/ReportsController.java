package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ReportExcelService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportExcelService excelService;

    public ReportsController(ReportExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/download")
    public void downloadExcel(
    		@RequestParam("year") int year,
    	    @RequestParam(value = "month", required = false) String month,
            HttpServletResponse response
    ) throws Exception {

        response.setContentType(
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        String fileName = (month == null)
                ? "Financial_Data_" + year + ".xlsx"
                : "Financial_Data_" + year + "_" + month + ".xlsx";

        response.setHeader(
          "Content-Disposition",
          "attachment; filename=" + fileName
        );

        excelService.generateExcel(year, month, response.getOutputStream());
    }
}

