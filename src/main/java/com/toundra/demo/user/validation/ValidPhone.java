package com.toundra.demo.user.validation;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PhoneValidator.class)
public @interface ValidPhone {
	
	String message() default "{InvalidPhoneNumberMessage}";
	int lower() default 0;
	int upper() default 15;
	
	String regex() default ".*";
	String regexAlt() default ".*";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	
}
