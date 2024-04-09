package it.alelegrand.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.alelegrand.spring.domain.User;

@Service
public interface UserService {
	
	public List<User> findAll();
	
	public User add(User user);
	
	public User update(User user);
	
	public User delete();
	
}