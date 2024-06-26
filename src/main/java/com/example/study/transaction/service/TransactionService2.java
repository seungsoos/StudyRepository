package com.example.study.transaction.service;

import com.example.study.common.entity.Author;
import com.example.study.common.entity.Book;
import com.example.study.common.repository.AuthorRepository;
import com.example.study.common.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService2 {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2() {
        log.info(" > 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2 start");

        Author author = new Author("다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2");
        Book book = new Book("다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2", "다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        log.info(" > 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2 end");
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 트랜잭션_전파_테스트_1_SUB() {
        try {
            System.out.println("==== 트랜잭션_전파_테스트_1_SUB start ===");
            Book book = new Book("title", "content");
            Book saveBook = bookRepository.save(book);
            System.out.println(saveBook);

            System.out.println("==== 트랜잭션_전파_테스트_1_SUB end ===");
            throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
