package com.example.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Income;
import com.example.entity.Expenses;
import com.example.entity.Savings;
import com.example.repository.ExpensesRepository;
import com.example.repository.IncomeRepository;
import com.example.repository.SavingsRepository;



@Service
public class ExcelUploadService {

    private final IncomeRepository incomeRepo;
    private final ExpensesRepository expensesRepo;
    private final SavingsRepository savingsRepo;

    public ExcelUploadService(
            IncomeRepository incomeRepo,
            ExpensesRepository expensesRepo,
            SavingsRepository savingsRepo) {
        this.incomeRepo = incomeRepo;
        this.expensesRepo = expensesRepo;
        this.savingsRepo = savingsRepo;
    }
    // ==============================
    private LocalDate readDate(Cell cell, DataFormatter formatter) {

        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC
                && DateUtil.isCellDateFormatted(cell)) {

            return cell.getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        String value = formatter.formatCellValue(cell);
        if (value == null || value.isBlank()) return null;

        return LocalDate.parse(value);
    }

    public void processExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook wb = WorkbookFactory.create(is)) {

            DataFormatter formatter = new DataFormatter();

            /* ===== INCOME ===== */
            Sheet incomeSheet = wb.getSheet("Income");
            if (incomeSheet != null) {
                for (int i = 1; i <= incomeSheet.getLastRowNum(); i++) {
                    Row r = incomeSheet.getRow(i);
                    if (r == null) continue;

                    String source = formatter.formatCellValue(r.getCell(0));
                    String amountStr = formatter.formatCellValue(r.getCell(2));
                    if (source.isBlank() || amountStr.isBlank()) continue;

                    LocalDate date = readDate(r.getCell(1), formatter);
                    if (date == null) continue;

                    Income inc = new Income();
                    inc.setUserId(1L);
                    inc.setSource(source);
                    inc.setDate(date);
                    inc.setAmount(new BigDecimal(amountStr));

                    incomeRepo.save(inc);
                }
            }


            /* ===== EXPENSES ===== */
            Sheet expSheet = wb.getSheet("Expenses");
            if (expSheet != null) {
                for (int i = 1; i <= expSheet.getLastRowNum(); i++) {
                    Row r = expSheet.getRow(i);
                    if (r == null) continue;

                    String details = formatter.formatCellValue(r.getCell(0));
                    String payment = formatter.formatCellValue(r.getCell(2));
                    String amountStr = formatter.formatCellValue(r.getCell(3));
                    if (details.isBlank() || amountStr.isBlank()) continue;

                    LocalDate date = readDate(r.getCell(1), formatter);
                    if (date == null) continue;

                    Expenses e = new Expenses();
                    e.setUserId(1L);
                    e.setExpenseDetails(details);
                    e.setPayment(payment);
                    e.setDate(date);
                    e.setAmount(Double.valueOf(amountStr));

                    expensesRepo.save(e);
                }
            }

            /* ===== SAVINGS ===== */
            Sheet savSheet = wb.getSheet("Savings");
            if (savSheet != null) {
                for (int i = 1; i <= savSheet.getLastRowNum(); i++) {
                    Row r = savSheet.getRow(i);
                    if (r == null) continue;

                    String details = formatter.formatCellValue(r.getCell(0));
                    String amountStr = formatter.formatCellValue(r.getCell(2));
                    if (details.isBlank() || amountStr.isBlank()) continue;

                    LocalDate date = readDate(r.getCell(1), formatter);
                    if (date == null) continue;

                    Savings s = new Savings();
                    s.setUserId(1L);
                    s.setDetails(details);
                    s.setDate(date);
                    s.setAmount(Double.valueOf(amountStr));

                    savingsRepo.save(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Excel upload failed", e);
        }
    }



}


