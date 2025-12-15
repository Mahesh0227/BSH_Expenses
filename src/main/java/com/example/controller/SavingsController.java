package com.example.controller;

import com.example.dto.SavingsDTO;
import com.example.entity.Savings;
import com.example.service.SavingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings")
@CrossOrigin
public class SavingsController {

    private final SavingsService service;

    public SavingsController(SavingsService service) {
        this.service = service;
    }

    /* ================= LIST ================= */
    @GetMapping
    public List<Savings> list(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) String month
    ) {
        return service.list(year, month);
    }

    /* ================= CREATE ================= */
    @PostMapping
    public ResponseEntity<Savings> create(@RequestBody SavingsDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    /* ================= UPDATE ================= */
    @PutMapping("/{id}")
    public ResponseEntity<Savings> update(
            @PathVariable(name = "id") Long id,
            @RequestBody SavingsDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    /* ================= delete ================= */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "id") Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
