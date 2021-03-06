package com.vmw.assignment.ng.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.assignment.ng.model.dto.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	List<Employee> findAllByNameIn(List<String> names);

	Optional<Employee> findByName(String name);

	@Modifying
	@Query(value = "DELETE FROM employee WHERE name = :name", nativeQuery = true)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void deleteByName(@Param("name") String name);

}
