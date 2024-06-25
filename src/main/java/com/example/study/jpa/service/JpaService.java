package com.example.study.jpa.service;

import com.example.study.common.entity.Test;
import com.example.study.common.repository.TestRepository;
import com.example.study.jpa.entity.PersistableImpl;
import com.example.study.jpa.repository.PersistableImplRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JpaService {

    private final TestRepository testRepository;
    private final JdbcTemplate jdbcTemplate;
    private final PersistableImplRepository persistableImplRepository;

    @Transactional(readOnly = true)
    public void findAll() {
        List<Test> all = testRepository.findAll();
        for (Test test : all) {
            System.out.println("test = " + test);
        }
    }

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


    /**
     * @Transactional
     *     public <S extends T> S save(S entity) {
     *         Assert.notNull(entity, "Entity must not be null");
     *         if (this.entityInformation.isNew(entity)) {
     *             this.entityManager.persist(entity);
     *             return entity;
     *         } else {
     *             return this.entityManager.merge(entity);
     *         }
     *     }
     *
     *     SimpleJpaRepository 의 save 구현체를 보면
     *     식별자가 있을경우 merge / 없으면 persist 로 동작한다.
     */

    /**
     * 먼저 Select 쿼리 발생 후
     * commit 시점에 Insert 발생
     */
    @Transactional
    public void String_ID_save쿼리가_날아가는시점() {
        System.out.println(" === save쿼리가_날아가는시점 start ===");
        Test test = new Test("id", "title", "content");
        testRepository.save(test);
        System.out.println(" === save쿼리가_날아가는시점 end ===");
    }

    /**
     * 마찬가지로 Select 쿼리 발생 후
     * commit 시점에 Insert 발생
     */
    @Transactional
    public void String_ID_save_후_로그찍어기() {
        System.out.println(" === String_ID_save_후_로그찍어기 start ===");
        Test test = new Test("id1", "title1", "content1");
        Test saveTest = testRepository.save(test);
        System.out.println("saveTest = " + saveTest);
        System.out.println(" === String_ID_save_후_로그찍어기 end ===");
    }

    @Transactional
    public void save_1000건_시간측정() {
        System.out.println(" === save test start ===");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            Test test = new Test("id" + i, "title" + i, "content" + i);
            testRepository.save(test);
        }

        stopWatch.stop();
        System.out.println("실행시간 = " + stopWatch.getTotalTime(TimeUnit.SECONDS));
        System.out.println(" === save test end ===");
    }

    @Transactional
    public void saveAll_1000건_시간측정() {
        System.out.println(" === saveAll start ===");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Test> list = new ArrayList<>();
        for (int i = 2000; i < 3000; i++) {
            Test test = new Test("id" + i, "title" + i, "content" + i);
            list.add(test);
        }
        testRepository.saveAll(list);

        stopWatch.stop();
        System.out.println("실행시간 = " + stopWatch.getTotalTime(TimeUnit.SECONDS));
        System.out.println(" === saveAll end ===");
    }


    /**
     * Persistable 의
     *
     * @Override
     *     public boolean isNew() {
     *         return true;
     *     }
     *
     *     구현으로 Merge가 아닌 persist 동작
     */
    @Transactional
    public void save시에_select_쿼리가_나가지않게() {
        System.out.println(" === save시에_select_쿼리가_나가지않게 start ===");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<PersistableImpl> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            PersistableImpl persistable = new PersistableImpl("id" + i, "title" + i, "content" + i);
            list.add(persistable);
        }
        persistableImplRepository.saveAll(list);

        stopWatch.stop();
        System.out.println("실행시간 = " + stopWatch.getTotalTime(TimeUnit.SECONDS));
        System.out.println(" === save시에_select_쿼리가_나가지않게 end ===");
    }
}
