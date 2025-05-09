package com.example.study.jpa.service;

import com.example.study.jpa.common.entity.Test;
import com.example.study.jpa.common.repository.TestRepository;
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
        System.out.println("=== 벌크인서트_테스트 start ===");
        String sql = "INSERT INTO test (id, title, content) VALUES (?, ?, ?)";

        List<Test> testList = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            Test test = new Test("id" + i, "title" + i, "content" + i);
            testList.add(test);
        }

        jdbcTemplate.batchUpdate(sql, testList, testList.size(),
                (PreparedStatement ps, Test test) -> {
                    ps.setString(1, test.getId());
                    ps.setString(2, test.getTitle());
                    ps.setString(3, test.getContent());
                });
        System.out.println("=== 벌크인서트_테스트 end ===");
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
     * save 시에
     * isNew 라면 persist
     * isNew 가 아니라면 merge 가발생하는데
     *
     * 기본 디폴트가 false 이기에 merge가 동작함.
     * merge 이기 때문에 select -> insert 가되고
     * 만약 영속성컨텍스트에 있다면 select -> update가 나간다.
     *
     */
    @Transactional
    public void String_ID_save쿼리가_날아가는시점() {
        System.out.println(" === save쿼리가_날아가는시점 start ===");

        Test findTest = testRepository.findById("id")
                .orElseThrow(RuntimeException::new);
        System.out.println("findTest = " + findTest);

        Test test = new Test("id", "title111", "content111");
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


    /**
     * 기본키를 사용하지않고 다른 조건절로 검색시
     * 1차 캐시를 사용하지않고 Select 쿼리가 나간다.
     *
     * 그이유는 1차캐시는 기본키를 통해 접근이 가능하기때문이다.
     */
    @Transactional(readOnly = true)
    public void 동일한_엔티티를_조건이_다르게_조회() {
        System.out.println("=== 동일한_엔티티를_조건이_다르게_조회 start ===");
        Test test = testRepository.findById("id1")
                .orElseThrow(RuntimeException::new);
        System.out.println("test = " + test);

        Test test2 = testRepository.findByTitle(test.getTitle())
                .orElseThrow(RuntimeException::new);
        System.out.println("test2 = " + test2);

        Test test1 = testRepository.findByTitle("title1")
                .orElseThrow(RuntimeException::new);
        System.out.println("test1 = " + test1);


        System.out.println("=== 동일한_엔티티를_조건이_다르게_조회 end ===");
    }
}
