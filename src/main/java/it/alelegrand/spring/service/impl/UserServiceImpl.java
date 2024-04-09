package it.alelegrand.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.alelegrand.spring.domain.User;
import it.alelegrand.spring.repository.UserRepository;
import it.alelegrand.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		List<User> users = (List<User>) userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		return users;
	}

	@Override
	public User add(User user) {
		User u = userRepository.save(user);
		
		return u;
	}

	@Override
	public User update(User user) {
		User u = userRepository.save(user);
		
		return u;
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id).get();
		
		return user;
	}
}