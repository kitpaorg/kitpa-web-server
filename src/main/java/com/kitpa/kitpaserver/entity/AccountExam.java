package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AccountExam extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="EXAM_ID")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;
    private Integer scoreSummary;

    @OneToMany(mappedBy = "accountExam")
    private List<ProblemScore> problemScores = new ArrayList<>();


//    @Column(nullable = false, columnDefinition = "boolean default false")
//    private Boolean deleted = false;

    public static AccountExam create(Account account, Exam exam) {
        AccountExam accountExam = new AccountExam();
        accountExam.account = account;
        accountExam.exam = exam;
        return accountExam;
    }

    /*
    public boolean isDeleted(){
        return this.deleted;
    }
    public void delete(){
        this.deleted = true;
    }
     */
}
