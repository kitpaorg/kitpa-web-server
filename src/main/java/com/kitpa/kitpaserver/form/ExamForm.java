package com.kitpa.kitpaserver.form;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExamForm {
    private List<Long> problemIds = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime examSolveDue;

    public boolean hasProblems() {
        return !problemIds.isEmpty();
    }
}
