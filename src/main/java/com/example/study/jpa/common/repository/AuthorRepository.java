package com.example.study.jpa.common.repository;

import com.example.study.jpa.common.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
