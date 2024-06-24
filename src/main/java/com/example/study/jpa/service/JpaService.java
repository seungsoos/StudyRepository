package com.example.study.jpa.service;

import com.example.study.common.entity.Test;
import com.example.study.common.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaService {

    private final TestRepository testRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void 벌크인서트_테스트() {

        String sql = "INSERT INTO test (id, title, content) VALUES (?, ?, ?)";

        List<Test> testList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Test test = new Test("id" + i, "title" + i, "content" + i);
            testList.add(test);
        }

        jdbcTemplate.batchUpdate(sql, testList, testList.size(),
                (PreparedStatement ps, Test test) -> {
                    ps.setString(1, test.getId());
                    ps.setString(2, test.getTitle());
                    ps.setString(3, test.getContent());
                });

    }

}
