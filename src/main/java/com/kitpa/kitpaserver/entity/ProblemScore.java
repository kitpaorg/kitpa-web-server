package com.kitpa.kitpaserver.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class ProblemScore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Problem problem;
    @ManyToOne
    private AccountExam accountExam;
    private Integer earnScore;
}
