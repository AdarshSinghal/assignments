package com.vmw.assignment.ng.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vmw.assignment.ng.model.dto.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	List<Employee> findAllByNameIn(List<String> names);

}
