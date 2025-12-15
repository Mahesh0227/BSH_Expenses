package com.example.repository;

import com.example.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findAllByOrderByDateDesc();

 // For "all years"
//    List<Savings> findAllByOrderByDateDesc();

    // For year / month filtering
    List<Savings> findByDateBetweenOrderByDateDesc(
        LocalDate startDate,
        LocalDate endDate
    );
    
    List<Savings> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);
}
