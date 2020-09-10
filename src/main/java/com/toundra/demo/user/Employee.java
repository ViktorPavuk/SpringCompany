package com.toundra.demo.user;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name =  "employee", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Employee {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Cannot be empty")
	@Size(min=2, max=30)
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Size(min=2, max=30)
	@Column(name = "last_name")
	private String lastName;
	
	//private SimpleDateFormat date;
	
	//@Temporal(TemporalType.DATE)
	//@Past
	//@Future(message = "* Birth date must be in the future")
//	@Past(message = "* Birth date must be in the past")
	private Date dateOfBirth = new Date(0);
	
	@Email(message = "* Please enter a valid email", regexp =".+@.+\\..+")
	private String email;
	
	private String department;
	
	@NotNull
	@Min(0)
	private int salary;
	
	public Employee() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
	public String salaryFormatted() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		//removes .00 from the end of an int
		return(formatter.format(salary).substring(0, formatter.format(salary).length() - 3));
	}
	
	@Override
	public String toString() {
		return(firstName + " " + lastName);
	}

}
