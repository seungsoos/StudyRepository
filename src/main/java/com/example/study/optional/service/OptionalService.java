package com.example.study.optional.service;

import com.example.study.jpa.common.entity.Author;
import com.example.study.jpa.common.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionalService {

    private final AuthorRepository authorRepository;

    /**
     * orElse 사용시 null일 경우 지정된 값을 return한다.
     */
    @Transactional(readOnly = true)
    public void orElse(Long id) {
        Author author = authorRepository.findById(id).orElse(null);

        System.out.println("author = " + author);
    }

    /**
     * orElse 와 비슷하지만 orElseGet()의 경우
     * supplier로 반환값이 필요함
     */
    @Transactional(readOnly = true)
    public void orElseGet(Long id) {
        Author author = authorRepository.findById(id).orElseGet(
                () -> {
                    Author defaultAuthor = new Author();
                    defaultAuthor.setName("test");
                    return defaultAuthor;
                });

        System.out.println("author = " + author);
    }

    /**
     * 값이 존재하는 경우와
     * 값이 존재하지 않는 경우 처리 로직으로 구성된다.
     *
     */
   @Transactional(readOnly = true)
    public void ifPresentOrElse(Long id) {
       Optional<Author> optionalAuthor = authorRepository.findById(id);

       optionalAuthor.ifPresentOrElse(
               author -> {
                   System.out.println("Author found: " + author.getName());
               },
               () -> {
                   System.out.println("Author not found.");
               }
       );
    }
}
