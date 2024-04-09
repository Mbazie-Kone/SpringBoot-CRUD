package it.alelegrand.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.alelegrand.spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}