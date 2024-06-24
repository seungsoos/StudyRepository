package com.example.study.GeneratedValue.controller;

import com.example.study.GeneratedValue.entity.Auto;
import com.example.study.GeneratedValue.entity.Identity;
import com.example.study.GeneratedValue.entity.Sequence;
import com.example.study.GeneratedValue.entity.Tables;
import com.example.study.GeneratedValue.repository.AutoRepository;
import com.example.study.GeneratedValue.repository.IdentityRepository;
import com.example.study.GeneratedValue.repository.SequenceRepository;
import com.example.study.GeneratedValue.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generatedValue")
@RequiredArgsConstructor
public class GeneratedValueController {

    private final AutoRepository autoRepository;
    private final IdentityRepository identityRepository;
    private final SequenceRepository sequenceRepository;
    private final TablesRepository tablesRepository;

    /**
     * @GeneratedValue(strategy = GenerationType.AUTO)
     *
     * 자동으로 Identity, Sequence, Table 전략 중 택 1을 하지만
     * 하이버네이트를 무조건 밎지말고 DB에 맞는 전략을 선탁해야한다.
     *
     * 하이버네이트 별로 mysql이라도 다른전략이 사용되며,
     * 현재는 Sequence 전략이다.
     *
     * Sequence 전략이기떄문에 시퀀스 테이블 조회 및 업데이트 후
     * commit 시점에 insert쿼리가 날라간다.
     *
     */
    @PostMapping("/auto")
    @Transactional
    public void auto() {
        System.out.println("=== auto start ===");
        Auto auto = new Auto("value");
        Auto save = autoRepository.save(auto);
        System.out.println("save = " + save);
        System.out.println("=== auto end ===");
    }

    /**
     * identity 전략은 mysql에게 PK 생성을 위임 하여
     * commit 시점이아닌 save() 시점에 insert 쿼리가 날라간다.
     *
     * 이후 mysql에서는 ID 값을 반환하기에 JPA 에서도 Return된 Entity에서 ID 값을 가지고있다.
     *
     */
    @PostMapping("/identity")
    @Transactional
    public void identity() {
        System.out.println("=== identity start ===");
        Identity identity = new Identity("value");
        Identity save = identityRepository.save(identity);
        System.out.println("save = " + save);
        System.out.println("=== identity end ===");
    }

    /**
     * auto가 현재 sequence 전략이기에 동일하다.
     */
    @PostMapping("/sequence")
    @Transactional
    public void sequence() {
        System.out.println("=== sequence start ===");
        Sequence sequence = new Sequence("value");
        Sequence save = sequenceRepository.save(sequence);
        System.out.println("save = " + save);
        System.out.println("=== sequence end ===");
    }

    /**
     * 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략이다
     * hibernate_sequences
     */
    @PostMapping("/table")
    @Transactional
    public void table() {
        System.out.println("=== table start ===");
        Tables tables = new Tables("value");
        Tables save = tablesRepository.save(tables);
        System.out.println("save = " + save);
        System.out.println("=== table end ===");
    }

}
