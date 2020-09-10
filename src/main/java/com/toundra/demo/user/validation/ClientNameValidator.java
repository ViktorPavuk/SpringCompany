package com.toundra.demo.user.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.toundra.demo.user.Client;

@Component
public class ClientNameValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Client.class.equals(clazz);
	}
	
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		//Check if field is null
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientName", "clientName.empty", "Client name cannot be empty");
		
		//Check if there's a space in the name
		String clientName = ((Client)object).getClientName();
		if (clientName.contains(" "))
			errors.rejectValue("clientName", "clientName.containsSpace", "Client name cannot contain spaces");
	}

}
