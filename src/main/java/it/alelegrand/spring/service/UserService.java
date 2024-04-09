package it.alelegrand.spring.service;

import java.util.List;

import it.alelegrand.spring.domain.User;

public interface UserService {
	
	public List<User> findAll();
	
	public User findById (Long id);
	
	public User add(User user);
	
	public User update(User user);
	
	public void delete(User user);
	
}