package kr.ac.poly.ex3.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex3.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface MemoRepository extends JpaRepository<Memo, Long> { // entity, 기본키의 타입
    // select 특정 mno 값의 범위의 값들을 내림차순으로 정렬
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // select 특정 mno 값의 범위의 값들을 내림차순으로 정렬, 페이징 처리
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    // delete 특정 mno 값보다 작거나 같은 행들을 삭제
    void deleteMemoByMnoLessThan(Long num);

    // @Query 를 사용하여 JPQL을 실행
    // select문 실행
    @Query("select m from Memo m order by m.mno DESC ")
    List<Memo> getListDesc();

    // @Query 를 사용하여 JPQL을 실행
    // update문 실행
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno= :mno")
    // 반환형이 int 인 이유는 update 하고 변경된 행 개수를 반환하기 때문이다.
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
        //
    int updateMemoText2(@Param("param") Memo memo);

    @Query(value = "select  m from Memo m where m.mno > :mno ",
     countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(@Param("mno") Long mno, Pageable pageable);

}
