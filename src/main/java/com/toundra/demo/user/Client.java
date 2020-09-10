package com.toundra.demo.user;


import java.text.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toundra.demo.user.validation.ValidPhone;

@Entity
@Table(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	private String clientName;
	
	@NotNull
	private String companyName;
	
	@Email
	private String email;
	
	@ValidPhone(lower = 0, upper = 12, regex = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", regexAlt ="^[2-9]\\d{9}")
	private String phone;
	
	private int sales;
	
	//Cannot be null
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "employee_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Employee employee;
	
	//Can be null (assigned later)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private Employee employee;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	
	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		//removes .00 from the end of an int
		return(formatter.format(sales).substring(0, formatter.format(sales).length() - 3));
	}

}
