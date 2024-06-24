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
public class TransactionService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private final TransactionService2 transactionService2;

    @Transactional(readOnly = true)
    public Author author(Long num) {
        Author author = authorRepository.findById(num).orElse(null);
        log.info("author = {}", author);
        return author;
    }

    @Transactional(readOnly = true)
    public Book book(Long num) {
        Book book = bookRepository.findById(num).orElse(null);
        log.info("book = {}", book);

        return book;
    }

    @Transactional
    public void save() {
        Author author = new Author("name");
        Book book = new Book("title", "content");
        author.setBookList(book);
        authorRepository.save(author);
    }

    @Transactional
    public void delete(Long num) {
        authorRepository.deleteById(num);
    }

    @Transactional
    public void 하나의_트랜잭션에서_예외발생_Case() {
        log.info(" > 하나의_트랜잭션에서_예외발생_Case start");
        Author author = new Author("name");
        Book book = new Book("title", "content");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        throw new RuntimeException();
    }

    @Transactional
    public void 정상Case_같은_서비스에서_트랜잭션_전파() {
        log.info(" > 정상Case_같은_서비스에서_트랜잭션_전파 start");
        Author author = new Author("name");
        Book book = new Book("title", "content");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        정상Case_같은_서비스에서_트랜잭션_전파_자식();
        log.info(" > 정상Case_같은_서비스에서_트랜잭션_전파 end");
    }

    public void 정상Case_같은_서비스에서_트랜잭션_전파_자식() {
        log.info(" > 정상Case_같은_서비스에서_트랜잭션_전파_자식 start");

        Author author = new Author("name2");
        Book book = new Book("title2", "content2");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        log.info(" > 정상Case_같은_서비스에서_트랜잭션_전파_자식 end");
    }

    @Transactional
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case() {
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case start");

        Author author = new Author("같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case");
        Book book = new Book("같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case", "같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식();
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식(){
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식 start");

        Author author = new Author("같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식");
        Book book = new Book("같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식", "같은_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_자식");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);


        throw new RuntimeException();
    }

    @Transactional
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case() {
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case start");

        Author author = new Author("같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case");
        Book book = new Book("같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case", "같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        try {
            같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case end");
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식(){
        log.info(" > 같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식 start");

        Author author = new Author("같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식");
        Book book = new Book("같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식", "같은_클래스_내_트랜잭션_자식메서드에서_예외보상_Case_자식");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);


        throw new RuntimeException();
    }


    @Transactional
    public void 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case() {
        log.info(" > 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case start");

        Author author = new Author("다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case");
        Book book = new Book("다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case", "다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case");
        author.setBookList(book);
        Author saveAuthor = authorRepository.save(author);
        log.info("saveAuthor = {}", saveAuthor);

        transactionService2.다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case_2();
        log.info(" > 다른_클래스_내_트랜잭션_자식메서드에서_예외발생_Case end");
    }


    /**
     * 트랜잭션이 선언되어 있지않는 시작지점이기에
     * 예외가발생하여도
     * 트랜잭션이 선언된 메서드는 롤백되지않는다.
     */
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case() {
        System.out.println(" === 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case start ===");

        for (int i = 0; i < 10; i++) {
            Author author = new Author("트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case");
            트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case(author);
        }

        System.out.println(" === 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case end ===");
        throw new RuntimeException();
    }

    @Transactional
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case(Author author) {
        authorRepository.save(author);
    }


    /**
     * 트랜잭션이 선언되어 있기떄문에 롤백된다.
     */
    @Transactional
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case() {
        System.out.println(" === 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case start ===");

        for (int i = 0; i < 10; i++) {
            Author author = new Author("트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case");
            트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case_1(author);
        }

        System.out.println(" === 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_정상_Case end ===");
        throw new RuntimeException();
    }

    @Transactional
    public void 트랜잭션은_처음시작하는_메서드를_기준으로_동작한다_예외발생_Case_1(Author author) {
        authorRepository.save(author);
    }
}
