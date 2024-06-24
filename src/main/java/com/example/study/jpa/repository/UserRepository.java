package com.example.study.jpa.repository;

import com.example.study.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
