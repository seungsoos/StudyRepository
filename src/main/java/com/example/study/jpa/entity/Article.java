package com.example.study.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
