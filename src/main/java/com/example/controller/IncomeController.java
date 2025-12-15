package com.example.controller;

import com.example.dto.IncomeDTO;
import com.example.entity.Income;
import com.example.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    private Income toEntity(IncomeDTO dto) {
        if (dto == null) return null;
        Income e = new Income();
        e.setId(dto.getId());
        e.setUserId(dto.getUserId());
        e.setSource(dto.getSource());
        e.setDate(dto.getDate());
        e.setAmount(dto.getAmount());
        return e;
    }

    private IncomeDTO toDto(Income e) {
        if (e == null) return null;
        IncomeDTO dto = new IncomeDTO();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setSource(e.getSource());
        dto.setDate(e.getDate());
        dto.setAmount(e.getAmount());
        return dto;
    }

    @PostMapping
    public ResponseEntity<IncomeDTO> create(@RequestBody IncomeDTO dto) {
        if (dto.getUserId() == null ||
            dto.getSource() == null || dto.getSource().isBlank() ||
            dto.getDate() == null ||
            dto.getAmount() == null || dto.getAmount().doubleValue() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Income saved = incomeService.save(toEntity(dto));
        IncomeDTO out = toDto(saved);
        return ResponseEntity.created(URI.create("/api/incomes/" + out.getId())).body(out);
    }

    // NEW: return all incomes (no filter)
    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getAll() {
        List<IncomeDTO> list = incomeService.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // existing: get by user (explicit path variable name)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IncomeDTO>> getByUser(@PathVariable("userId") Long userId) {
        List<IncomeDTO> dtoList = incomeService.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeDTO> getOne(@PathVariable("id") Long id) {
        return incomeService.findById(id)
                .map(income -> ResponseEntity.ok(toDto(income)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeDTO> update(@PathVariable("id") Long id, @RequestBody IncomeDTO dto) {
        return incomeService.findById(id)
                .map(existing -> {
                    if (dto.getSource() != null) existing.setSource(dto.getSource());
                    if (dto.getDate() != null) existing.setDate(dto.getDate());
                    if (dto.getAmount() != null) existing.setAmount(dto.getAmount());
                    Income updated = incomeService.save(existing);
                    return ResponseEntity.ok(toDto(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        incomeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
