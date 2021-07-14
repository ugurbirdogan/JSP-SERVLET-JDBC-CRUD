package com.ugur.model;

public class Employee {

	private int id;
	private String name;
	private String surName;
	private String email;
	private String address;
	private int salary;
	
	public Employee() {
		super();
	}
	
	
	
	public Employee(String name, String surName, String email, String address, int salary) {
		super();
		this.name = name;
		this.surName = surName;
		this.email = email;
		this.address = address;
		this.salary = salary;
	}



	public Employee(int id, String name, String surName, String email, String address, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.surName = surName;
		this.email = email;
		this.address = address;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
	
	
	
}
