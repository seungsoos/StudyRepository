package com.example.study.jpa.service;

import com.example.study.jpa.entity.Article;
import com.example.study.jpa.entity.User;
import com.example.study.jpa.repository.ArticleRepository;
import com.example.study.jpa.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JpaServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void beforeEach() {

        Query disableForeignKeyChecks = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0");
        disableForeignKeyChecks.executeUpdate();

        Query truncateUserTable = entityManager.createNativeQuery("TRUNCATE TABLE user");
        truncateUserTable.executeUpdate();

        Query truncateArticleTable = entityManager.createNativeQuery("TRUNCATE TABLE article");
        truncateArticleTable.executeUpdate();

        Query enableForeignKeyChecks = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1");
        enableForeignKeyChecks.executeUpdate();


        User user = new User("name");
        Article article1 = new Article("title", "content");
        Article article2 = new Article("title", "content");
        Article article3 = new Article("title", "content");

        user.changeArticles(article1);
        user.changeArticles(article2);
        user.changeArticles(article3);
        userRepository.save(user);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("")
    void JpaServiceTest() {
        System.out.println("== start ==");
        User user = userRepository.findById(1L)
                .orElseThrow(RuntimeException::new);
        System.out.println("== end ==");
        System.out.println(user.getName());
    }

}