package com.expense.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expense.entity.Expense;

public interface ExpenseReposiotry extends JpaRepository<Expense,Integer> {
	
	@Query("SELECT e FROM Expense e WHERE e.users.id=?1 AND e.category=?2")
	Page<Expense> findByUserAndCategory(Integer id,String category,Pageable pageable);
	
	/*
	 * @Query("SELECT e FROM Expense e WHERE e.users.id=?1 AND e.name LIKE %?#{escape(keyword)}% escape ?#{escapeCharacter()}"
	 * ) Page<Expense> findByUserIdAndName(Integer id,String keyword,Pageable
	 * pageable);
	 */
	
	@Query("SELECT e FROM Expense e WHERE e.users.id=?1")
	Page<Expense> findExpenseById(Integer id,Pageable pageable);
	
	@Query("SELECT e FROM Expense e WHERE e.users.id=?1 AND e.id=?2")
	Optional<Expense> findByUserIdAndId(Integer userId,Integer id);
}
