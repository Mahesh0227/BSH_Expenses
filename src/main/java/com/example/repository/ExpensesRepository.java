package com.example.repository;

import com.example.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByUserId(Long userId);
    @Query("""
        select e from Expenses e
        where e.date between :start and :end
        order by e.date desc
    """)
    List<Expenses> findByDateBetween(LocalDate start, LocalDate end);
    List<Expenses> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);

}
