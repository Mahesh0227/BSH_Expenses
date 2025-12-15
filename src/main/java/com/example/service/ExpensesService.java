package com.example.service;

import com.example.dto.ExpensesDto;
import com.example.entity.Expenses;
import com.example.repository.ExpensesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class ExpensesService {

    private final ExpensesRepository repo;

    public ExpensesService(ExpensesRepository repo) {
        this.repo = repo;
    }

    // Convert entity -> dto
    private ExpensesDto toDto(Expenses e) {
        ExpensesDto d = new ExpensesDto();
        d.setId(e.getId());
        d.setUserId(e.getUserId());
        d.setExpenseDetails(e.getExpenseDetails());
        d.setDate(e.getDate());
        d.setPayment(e.getPayment());
        d.setAmount(e.getAmount());
        d.setAttachmentUrl(e.getAttachmentUrl());
        d.setWarrantyUrl(e.getWarrantyUrl());
        return d;
    }

    // Convert dto -> entity
    private Expenses toEntity(ExpensesDto d) {
        Expenses e = new Expenses();
        if (d.getId() != null) e.setId(d.getId());
        e.setUserId(d.getUserId());
        e.setExpenseDetails(d.getExpenseDetails());
        e.setDate(d.getDate());
        e.setPayment(d.getPayment());
        e.setAmount(d.getAmount());
        e.setAttachmentUrl(d.getAttachmentUrl());
        e.setWarrantyUrl(d.getWarrantyUrl());
        return e;
    }

    public List<ExpensesDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ExpensesDto> findAllByUserId(Long userId) {
        return repo.findAllByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public ExpensesDto findById(Long id) {
        return repo.findById(id).map(this::toDto).orElse(null);
    }

    public ExpensesDto create(ExpensesDto dto) {
        Expenses e = toEntity(dto);
        // ensure id null for create
        e.setId(null);
        Expenses saved = repo.save(e);
        return toDto(saved);
    }

    public ExpensesDto update(Long id, ExpensesDto dto) {
        Optional<Expenses> opt = repo.findById(id);
        if (!opt.isPresent()) return null;
        Expenses e = opt.get();
        // patch allowed fields
        e.setExpenseDetails(dto.getExpenseDetails());
        e.setDate(dto.getDate());
        e.setPayment(dto.getPayment());
        e.setAmount(dto.getAmount());
        e.setAttachmentUrl(dto.getAttachmentUrl());
        e.setWarrantyUrl(dto.getWarrantyUrl());
        // userId should normally not change; but if needed:
        if (dto.getUserId() != null) e.setUserId(dto.getUserId());
        Expenses updated = repo.save(e);
        return toDto(updated);
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
