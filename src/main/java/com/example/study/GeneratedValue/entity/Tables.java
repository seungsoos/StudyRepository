package com.example.study.GeneratedValue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String value;

    public Tables(String value) {
        this.value = value;
    }
}
