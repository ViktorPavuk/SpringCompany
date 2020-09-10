package com.toundra.demo.user;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
