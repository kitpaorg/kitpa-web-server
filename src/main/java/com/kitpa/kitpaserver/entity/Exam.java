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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(mappedBy = "exam")
    private List<Problem> problems = new ArrayList<>();
    private LocalDateTime receiptStartDate;
    private LocalDateTime receiptEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static Exam create(String title,
                              LocalDateTime receiptStartDate,
                              LocalDateTime receiptEndDate,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              List<Problem> problems) {
        Exam exam = new Exam();
        if (problems != null && !problems.isEmpty()) {
            exam.problems = problems;
            problems.forEach(p -> p.setExam(exam));
        }
        exam.title = title;
        exam.receiptStartDate = receiptStartDate;
        exam.receiptEndDate = receiptEndDate;
        exam.startDate = startDate;
        exam.endDate = endDate;
        return exam;
    }

    public boolean canCancel(){
        return receiptEndDate.isAfter(LocalDateTime.now());
    }
}
