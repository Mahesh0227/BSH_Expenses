package com.example.repository;

import com.example.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
	List<Income> findByUserId(Long userId);
    @Query("""
       SELECT i FROM Income i
       WHERE YEAR(i.date) = :year
       ORDER BY i.date DESC
    """)
    List<Income> findByYear(@Param("year") int year);

    @Query("""
       SELECT i FROM Income i
       WHERE YEAR(i.date) = :year
       AND FUNCTION('MONTHNAME', i.date) = :month
       ORDER BY i.date DESC
    """)
    List<Income> findByYearAndMonth(
        @Param("year") int year,
        @Param("month") String month
    );
    List<Income> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);

}
