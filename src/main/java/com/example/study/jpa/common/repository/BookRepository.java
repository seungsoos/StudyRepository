package com.example.study.jpa.common.repository;

import com.example.study.jpa.common.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
