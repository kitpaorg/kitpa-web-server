package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer problemNumber;
    private String title;
    private Integer maxScore;
    @Enumerated(EnumType.STRING)
    private ProblemType type;
    private String answer;
    @Column(length = 50000)
    private String content;
    @ManyToOne
    private Exam exam;

    @Builder
    public Problem(Integer problemNumber,
                   String title,
                   Integer maxScore,
                   ProblemType type,
                   String answer,
                   String content) {
        this.problemNumber = problemNumber;
        this.title = title;
        this.maxScore = maxScore;
        this.type = type;
        this.answer = answer;
        this.content = content;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void update(Integer problemNumber,
                       String title,
                       Integer maxScore,
                       ProblemType type,
                       String answer,
                       String content) {
        this.problemNumber = problemNumber;
        this.title = title;
        this.maxScore = maxScore;
        this.type = type;
        this.answer = answer;
        this.content = content;
    }
}
