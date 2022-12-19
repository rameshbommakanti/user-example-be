package com.myproject.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.demo.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
