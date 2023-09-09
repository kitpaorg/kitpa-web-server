package com.kitpa.kitpaserver.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class AccountProblem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer problemNumber;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void setAnswer(String answer){
        this.answer = answer;
    }
    public static AccountProblem create(Integer problemNumber, String answer, Account account) {
        AccountProblem accountProblem = new AccountProblem();
        accountProblem.problemNumber = problemNumber;
        accountProblem.answer = answer;
        accountProblem.account = account;
        account.getAccountProblems().add(accountProblem);
        return accountProblem;
    }
}
