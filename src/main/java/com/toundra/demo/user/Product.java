package com.toundra.demo.user;


import java.text.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;


@Entity
@Table(name = "product")
public class Product {
	

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(min=2, max=30)
	private String name;
	
	@NotNull
	@Size(min=2, max=30)
	private String brand;
	
	@NotNull
	@Size(min=2, max=30)
	private String madein;
	
	@NotNull
	@DecimalMin(value = "0.0")
	private float price;


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return(formatter.format(price));
	}



	public String getMadein() {
		return madein;
	}

	public void setMadein(String madein) {
		this.madein = madein;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public Product() {
	}
	
}