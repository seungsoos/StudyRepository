package com.example.study.common.repository;

import com.example.study.common.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, String> {


    Optional<Test> findByTitle(String title);
}
