package com.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tournament.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

}
