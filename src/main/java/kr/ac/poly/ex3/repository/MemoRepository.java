package kr.ac.poly.ex3.repository;

import kr.ac.poly.ex3.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> { // entity, 기본키의 타입

}
