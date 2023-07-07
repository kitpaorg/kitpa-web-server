package com.kitpa.kitpaserver.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExamDto {
    private Long id;
    private List<ProblemDto> problemDtos = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime examSolveDue;
}
