package com.example.service;

import com.example.entity.Income;
import com.example.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IncomeService {

    private final IncomeRepository repo;

    public IncomeService(IncomeRepository repo) {
        this.repo = repo;
    }

    public Income save(Income income) {
        return repo.save(income);
    }

    public List<Income> findAll() {
        return repo.findAll();
    }

    public List<Income> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public Optional<Income> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
