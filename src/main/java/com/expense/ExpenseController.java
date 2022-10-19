package com.expense;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expense.entity.Expense;
import com.expense.service.ExpenseService;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/expense")
	public List<Expense> allExpense(Pageable pageable) {
		return expenseService.allExpense(pageable).toList();
	}
	
	@GetMapping("/expense/{id}")
	public Expense getById(@PathVariable("id") Integer id) {
		return expenseService.getById(id);		
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expense")
	public void deleteById(@RequestParam("id") Integer id) {
		expenseService.deleteById(id);		
	}
	
	//map json to java(request body)
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expense")
	public void saveExpense(@Valid @RequestBody Expense expense) {
		expenseService.save(expense);
	}
	
	@PutMapping("/expense/{id}")
	public Expense updateExpense(@RequestBody Expense expense,@PathVariable("id") Integer id) {
		return expenseService.updateExpense(id, expense);
	}
	
	//paging and sorting.
	//pageable is an interface provides pagination.
	//Page is an interface which is sublist of a list of objects.
	
	@GetMapping("/expense/category")
	public List<Expense> byCategory(@RequestParam("category")String category,Pageable pageable){
		return expenseService.findByCategory(category,pageable).toList();
	}
	
	/*
	 * @GetMapping("/expense/name") public
	 * List<Expense>byKeyword(@RequestParam("name")String name,Pageable pageable){
	 * return expenseService.findByKeyword(name, pageable).toList(); }
	 */
	
}
