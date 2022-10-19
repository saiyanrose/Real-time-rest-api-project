package com.expense.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.expense.entity.Users;

public interface UserRepository extends CrudRepository<Users,Integer> {
	
	@Query("SELECT u FROM Users u WHERE u.email=?1")
	public Users findByEmail(String email);	
	
}
