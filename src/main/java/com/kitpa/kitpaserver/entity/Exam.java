package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Exam extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Problem> problems = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime examSolveDue;

    public static Exam create(LocalDateTime startDate,
                              LocalDateTime endDate,
                              LocalDateTime examSolveDue,
                              List<Problem> problems) {
        Exam exam = new Exam();
        if(problems != null && !problems.isEmpty()){
            exam.problems = problems;
        }
        exam.startDate = startDate;
        exam.endDate = endDate;
        exam.examSolveDue = examSolveDue;
        return exam;
    }
}
