package com.kts.sigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kts.sigma.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
