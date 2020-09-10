package com.toundra.demo.user.validation;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//import javax.validation.constraints.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

	private int lower;
	private int upper;
	
	private String regex;
	private String regexAlt;
	
	@Override
	public void initialize(ValidPhone phone) {
		// TODO Auto-generated method stub
		this.lower = phone.lower();
		this.upper = phone.upper();
		this.regex = phone.regex();
		this.regexAlt = phone.regexAlt();

	}

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub

		Pattern pt = Pattern.compile(regex);
		Pattern ptAlt = Pattern.compile(regexAlt);
		Matcher mt = pt.matcher(phone);
		Matcher mtAlt = ptAlt.matcher(phone);	
		
		if(phone.length() == 0) {
			return true;
		} else if (mt.matches() || mtAlt.matches()) {
			return true;
		} else if(phone.length() < lower || phone.length() > upper)
			return false;
		else
			return false;
		

	}
	
	
	
}
