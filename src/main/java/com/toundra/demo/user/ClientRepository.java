package com.toundra.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	//User findByEmail(String email);
	List<Client> findByEmployeeId(int employeeId);
}
