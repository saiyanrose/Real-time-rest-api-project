package com.expense.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tbl_users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "name should not be empty")
	private String name;
	
	@Column(unique = true)
	@NotNull(message = "email should not be empty")
	@Email(message = "enter a valid email")
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(min = 5,message = "password should be atleast 5 characters")	
	@NotNull(message = "password must not be null")	
	private String password;
	
	private Integer age;
	
	@Column(name="created_at",nullable = false,updatable = false)
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name="updated_at",nullable = false,updatable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;
	
	public Users() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
