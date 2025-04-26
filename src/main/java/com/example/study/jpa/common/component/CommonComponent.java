package com.example.study.jpa.common.component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonComponent {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/truncate")
    @Transactional
    public void truncate() {
        System.out.println(" > truncate start ");

        // 외래 키 제약 조건 비활성화
        Query disableForeignKeyChecks = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0");
        disableForeignKeyChecks.executeUpdate();

        // 테이블 데이터 삭제
        Query truncateBookTable = entityManager.createNativeQuery("TRUNCATE TABLE book");
        truncateBookTable.executeUpdate();

        Query truncateAuthorTable = entityManager.createNativeQuery("TRUNCATE TABLE author");
        truncateAuthorTable.executeUpdate();

        Query truncateTestTable = entityManager.createNativeQuery("TRUNCATE TABLE test");
        truncateTestTable.executeUpdate();

        // 외래 키 제약 조건 활성화
        Query enableForeignKeyChecks = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1");
        enableForeignKeyChecks.executeUpdate();

        System.out.println(" > truncate end ");
    }
}
