package com.expense.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.expense.entity.Expense;
import com.expense.exceptions.ResourceNotFoundException;
import com.expense.repository.ExpenseReposiotry;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseReposiotry  expenseReposiotry;	
	
	@Autowired
	private UserService userService;
	
	public Page<Expense>allExpense(Pageable pageable){
		return expenseReposiotry.findExpenseById(userService.loggedInUser().getId(), pageable);
	}
	
	public Expense getById(Integer id) {
		Optional<Expense>expense=expenseReposiotry.findByUserIdAndId(userService.loggedInUser().getId(), id);
		if(expense.isPresent()) {
			return expense.get();
		}else {
			//throw new RuntimeException("Expense Not Found with id: "+id);
			throw new ResourceNotFoundException("Expense Not Found with id: "+id);
		}
	}

	public void deleteById(Integer id) {
		Optional<Expense>expense=expenseReposiotry.findById(id);
		if(expense.isPresent()) {
			expenseReposiotry.deleteById(id);	
		}else {
			throw new ResourceNotFoundException("Expense Not Found with id: "+id);
		}
			
	}

	public void save(Expense expense) {
		expense.setUsers(userService.loggedInUser());
		expenseReposiotry.save(expense);		
	}
	
	public Expense updateExpense(Integer id,Expense expense) {
		Expense existingExpense=getById(id);
		existingExpense.setName(expense.getName()!=null ? expense.getName() :existingExpense.getName());
		existingExpense.setDescription(expense.getDescription()!=null ? expense.getDescription() :existingExpense.getDescription());
		existingExpense.setCategory(expense.getCategory()!=null ? expense.getCategory() :existingExpense.getCategory());
		existingExpense.setAmount(expense.getAmount()!=null ? expense.getAmount():existingExpense.getAmount());
		return expenseReposiotry.save(existingExpense);
	}
	
	public Page<Expense>findByCategory(String category,Pageable pageable){
		return expenseReposiotry.findByUserAndCategory(userService.loggedInUser().getId(),category,pageable);
	}
	
	/*
	 * public Page<Expense>findByKeyword(String keyword,Pageable pageable){ return
	 * expenseReposiotry.findByUserIdAndName(userService.loggedInUser().getId(),
	 * keyword,pageable); }
	 */	
	
	
}
