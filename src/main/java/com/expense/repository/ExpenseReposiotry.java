package com.expense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expense.entity.Expense;

public interface ExpenseReposiotry extends JpaRepository<Expense,Integer> {
	Page<Expense> findByCategory(String category,Pageable pageable);
	
	@Query("SELECT e FROM Expense e WHERE e.name LIKE %:keyword%")
	Page<Expense> findByName(String keyword,Pageable pageable);
}
