package com.example.study.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "PersistableImpl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersistableImpl implements Persistable<String> {

    @Id
    private String id;

    private String title;

    private String content;

    @Override
    public boolean isNew() {
        return true;
    }
}
