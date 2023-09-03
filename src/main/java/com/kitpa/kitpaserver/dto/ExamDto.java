package com.kitpa.kitpaserver.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExamDto {
    private Long id;
    private String title;
    private List<ProblemDto> problems = new ArrayList<>();
    private LocalDateTime receiptIdleDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
