package com.toundra.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	boolean existsEmployeeByEmail(String email);
}