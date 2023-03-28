package kr.ac.poly.ex3.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno ;

    @Column(length = 200, nullable = false)
    private String memoText;


    public void setMno(int mno) {
        this.mno = mno;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }
}
