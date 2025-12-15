package com.example.service;

import com.example.dto.SavingsDTO;
import com.example.entity.Savings;
import com.example.repository.SavingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SavingsService {

    private final SavingsRepository repo;

    public SavingsService(SavingsRepository repo) {
        this.repo = repo;
    }

    /* ================= CREATE ================= */
    public Savings create(SavingsDTO dto) {

        Savings s = new Savings();
        s.setUserId(dto.getUserId());
        s.setDetails(dto.getDetails());
        s.setDate(dto.getDate());
        s.setAmount(dto.getAmount());
        s.setCreatedAt(LocalDateTime.now());

        return repo.save(s);
    }

    /* ================= UPDATE ================= */
    public Savings update(Long id, SavingsDTO dto) {

        Savings s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Saving not found"));

        s.setDetails(dto.getDetails());
        s.setDate(dto.getDate());
        s.setAmount(dto.getAmount());
        s.setUpdatedAt(java.time.LocalDateTime.now());

        return repo.save(s);
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /* ================= LIST ================= */
    public List<Savings> list(Integer year, String month) {

        if (year == null) {
            return repo.findAllByOrderByDateDesc();
        }

        LocalDate start;
        LocalDate end;

        if (month == null || month.equalsIgnoreCase("ALL")) {
            start = LocalDate.of(year, 1, 1);
            end   = LocalDate.of(year, 12, 31);
        } else {
            int m = java.time.Month.valueOf(month.toUpperCase()).getValue();
            start = LocalDate.of(year, m, 1);
            end   = start.withDayOfMonth(start.lengthOfMonth());
        }

        return repo.findByDateBetweenOrderByDateDesc(start, end);
    }
}
