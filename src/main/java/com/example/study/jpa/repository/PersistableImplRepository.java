package com.example.study.jpa.repository;

import com.example.study.jpa.entity.PersistableImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistableImplRepository extends JpaRepository<PersistableImpl, String> {
}
