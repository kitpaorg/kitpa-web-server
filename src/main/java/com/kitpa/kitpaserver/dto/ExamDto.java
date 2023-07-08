package com.kitpa.kitpaserver.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExamDto {
    private Long id;
    private List<ProblemDto> problems = new ArrayList<>();
    private LocalDateTime receiptStartDate;
    private LocalDateTime receiptEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
