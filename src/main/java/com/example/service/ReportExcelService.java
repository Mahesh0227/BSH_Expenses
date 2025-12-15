package com.example.service;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.*;

import com.example.entity.Expenses;
import com.example.entity.Income;
import com.example.entity.Savings;
import com.example.repository.ExpensesRepository;
import com.example.repository.IncomeRepository;
import com.example.repository.SavingsRepository;

@Service
public class ReportExcelService {

    private final IncomeRepository incomeRepo;
    private final ExpensesRepository expensesRepo;
    private final SavingsRepository savingsRepo;

    public ReportExcelService(
            IncomeRepository incomeRepo,
            ExpensesRepository expensesRepo,
            SavingsRepository savingsRepo
    ) {
        this.incomeRepo = incomeRepo;
        this.expensesRepo = expensesRepo;
        this.savingsRepo = savingsRepo;
    }

    public void generateExcel(int year, String month, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();

        LocalDate startDate;
        LocalDate endDate;

        if (month == null || month.equalsIgnoreCase("ALL")) {
            startDate = LocalDate.of(year, 1, 1);
            endDate   = LocalDate.of(year, 12, 31);
        } else {
            int m = Month.valueOf(month.toUpperCase()).getValue();
            startDate = LocalDate.of(year, m, 1);
            endDate   = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }

        // ---- CALCULATE TOTALS ONCE ----
        double totalIncome =
                incomeRepo.findByDateBetweenOrderByDateAsc(startDate, endDate)
                          .stream().mapToDouble(i -> i.getAmount().doubleValue()).sum();

        double totalExpense =
                expensesRepo.findByDateBetweenOrderByDateAsc(startDate, endDate)
                            .stream().mapToDouble(e -> e.getAmount().doubleValue()).sum();

        double totalSavings =
                savingsRepo.findByDateBetweenOrderByDateAsc(startDate, endDate)
                           .stream().mapToDouble(s -> s.getAmount().doubleValue()).sum();

        // ---- SHEETS ----
        writeSummarySheet(wb, startDate, endDate);
       
        writeIncomeSheet(wb, startDate, endDate);
        writeExpenseSheet(wb, startDate, endDate);
        writeSavingsSheet(wb, startDate, endDate);

        wb.write(out);
        wb.close();
    }


    /* ---------- SUMMARY (ROWS ONLY) ---------- */
    private void writeSummarySheet(Workbook wb, LocalDate start, LocalDate end) {

        Sheet sheet = wb.createSheet("Summary");

        double totalIncome =
                incomeRepo.findByDateBetweenOrderByDateAsc(start, end)
                          .stream()
                          .mapToDouble(i -> i.getAmount().doubleValue())
                          .sum();

        double totalExpense =
                expensesRepo.findByDateBetweenOrderByDateAsc(start, end)
                            .stream()
                            .mapToDouble(e -> e.getAmount().doubleValue())
                            .sum();

        double totalSavings =
                savingsRepo.findByDateBetweenOrderByDateAsc(start, end)
                           .stream()
                           .mapToDouble(s -> s.getAmount().doubleValue())
                           .sum();

        double balance = totalIncome - totalExpense;

        int r = 0;

        sheet.createRow(r++)
             .createCell(0)
             .setCellValue("Total Income : " + totalIncome);

        sheet.createRow(r++)
             .createCell(0)
             .setCellValue("Total Expenses : " + totalExpense);

        sheet.createRow(r++)
             .createCell(0)
             .setCellValue("Total Savings : " + totalSavings);

        sheet.createRow(r++)
             .createCell(0)
             .setCellValue("Net Balance : " + balance);

        sheet.autoSizeColumn(0);
    }


    /* ---------- INCOME ---------- */
    private void writeIncomeSheet(Workbook wb, LocalDate start, LocalDate end) {

        Sheet sheet = wb.createSheet("Income");

        Row h = sheet.createRow(0);
        h.createCell(0).setCellValue("Source");
        h.createCell(1).setCellValue("Date");
        h.createCell(2).setCellValue("Amount");

        List<Income> list =
                incomeRepo.findByDateBetweenOrderByDateAsc(start, end);

        int r = 1;
        for (Income i : list) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(i.getSource());
            row.createCell(1).setCellValue(i.getDate().toString());
            row.createCell(2).setCellValue(i.getAmount().doubleValue());
        }
    }

    /* ---------- EXPENSE ---------- */
    private void writeExpenseSheet(Workbook wb, LocalDate start, LocalDate end) {

        Sheet sheet = wb.createSheet("Expenses");

        Row h = sheet.createRow(0);
        h.createCell(0).setCellValue("Details");
        h.createCell(1).setCellValue("Date");
        h.createCell(2).setCellValue("Payment");
        h.createCell(3).setCellValue("Amount");

        List<Expenses> list =
                expensesRepo.findByDateBetweenOrderByDateAsc(start, end);

        int r = 1;
        for (Expenses e : list) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(e.getExpenseDetails());
            row.createCell(1).setCellValue(e.getDate().toString());
            row.createCell(2).setCellValue(e.getPayment());
            row.createCell(3).setCellValue(e.getAmount().doubleValue());
        }
    }

    /* ---------- SAVINGS ---------- */
    private void writeSavingsSheet(Workbook wb, LocalDate start, LocalDate end) {

        Sheet sheet = wb.createSheet("Savings");

        Row h = sheet.createRow(0);
        h.createCell(0).setCellValue("Details");
        h.createCell(1).setCellValue("Date");
        h.createCell(2).setCellValue("Amount");

        List<Savings> list =
                savingsRepo.findByDateBetweenOrderByDateAsc(start, end);

        int r = 1;
        for (Savings s : list) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(s.getDetails());
            row.createCell(1).setCellValue(s.getDate().toString());
            row.createCell(2).setCellValue(s.getAmount().doubleValue());
        }
    }
    

    


}
