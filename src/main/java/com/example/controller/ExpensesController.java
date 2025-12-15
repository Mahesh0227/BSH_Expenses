package com.example.controller;

import com.example.dto.ExpensesDto;
import com.example.service.ExpensesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    // GET all / filter by userId
    @GetMapping
    public ResponseEntity<List<ExpensesDto>> getAll(
            @RequestParam(value = "userId", required = false) Long userId) {

        if (userId != null) {
            return ResponseEntity.ok(service.findAllByUserId(userId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<ExpensesDto> getById(
            @PathVariable("id") Long id) {

        ExpensesDto dto = service.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ExpensesDto> create(
            @RequestBody ExpensesDto dto) {

        if (dto.getUserId() == null ||
            dto.getExpenseDetails() == null ||
            dto.getDate() == null ||
            dto.getAmount() == null) {

            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(service.create(dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ExpensesDto> update(
            @PathVariable("id") Long id,
            @RequestBody ExpensesDto dto) {

        ExpensesDto updated = service.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id) {

        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
