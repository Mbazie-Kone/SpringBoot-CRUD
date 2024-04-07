package it.alelegrand.spring.service;

import org.springframework.data.jpa.repository.JpaRepository;

import it.alelegrand.spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}