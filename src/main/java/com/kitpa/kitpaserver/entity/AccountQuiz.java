package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AccountQuiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Problem problem;

    private Long earnScore;
}
