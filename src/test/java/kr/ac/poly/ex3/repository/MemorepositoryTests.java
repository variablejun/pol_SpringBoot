package kr.ac.poly.ex3.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex3.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;

import javax.print.attribute.standard.PageRanges;

import java.awt.*;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemorepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }


    // sql 실행 시점이 find는 먼저 sql이 실행되고 getOne은 나중에 실행된다.
    @Test
    public void testSelect(){
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=================");
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }
    /*
    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);
        System.out.println("><><><><><><><><><><");
        System.out.println(memo);

    }*/

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100).memoText("Update Text").build();
        // 100인 mno가 있으면 update 없으면 insert mno는 객체
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test

    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("=======================");
        
        System.out.println("Total Pages: "+  result.getTotalPages()); // 총 몇 페이지
        System.out.println("Total Count: "+  result.getTotalElements()); // 전체 개수
        System.out.println("Page Number: "+  result.getNumber()); // 현제 페이지 번호 0부터 시작

        System.out.println("Page Size: "+  result.getSize()); // 페이지당 데이터 개수
        System.out.println("has next page?:"+  result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("is first page: "+  result.isFirst()); // 시작 페이지(0) 여부
        System.out.println("=======================");

        for (Memo memo : result.getContent()){ //99
            System.out.println(memo);
        }
    }

    @Test
    public void testSort(){
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethod(){
        List<Memo> list= memoRepository.findByMnoBetweenOrderByMnoDesc(50L, 80L);
        for (Memo memo: list){
            System.out.println(memo);
        }

    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(20L, 50L, pageable);
        result.get().forEach(
                memo -> System.out.println(memo)
        );

        //for (Memo memo: result){
        //   System.out.println(memo);
        //}

    }

    @Test
    @Commit // 없으면 반영안됨
    @Transactional // 없으면 에러 발생
    public void testDeleteQueryMetho(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    @Query
    public void testGetListDesc(){
        List<Memo> list= memoRepository.getListDesc();

    }
    @Test
    public void testUpdateMemoText(){
        int updateCount = memoRepository.updateMemoText(30L, "mno가 30인 내용 수정");
    }
    @Test
    public void testUpdateMemoText2(){
        Memo memo = new Memo();
        memo.setMno(31);
        memo.setMemoText("Memo 객체 참조값을 파라메타로 사용");
        int updateCount = memoRepository.updateMemoText2(memo);
    }
    @Test
    public void testgetListWithQuery(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").ascending());
        Page<Memo> result = memoRepository.getListWithQuery(32L,pageable);

        result.get().forEach(
                memo -> System.out.println(memo)
        );
    }
}
