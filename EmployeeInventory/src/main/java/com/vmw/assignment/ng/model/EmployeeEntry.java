package com.vmw.assignment.ng.model;

/**
 * @author adarsh
 *
 */
public class EmployeeEntry {

	private String name;
	private Integer age;

	public EmployeeEntry() {

	}

	public EmployeeEntry(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
