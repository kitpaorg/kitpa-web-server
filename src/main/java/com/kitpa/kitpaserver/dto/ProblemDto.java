package com.kitpa.kitpaserver.dto;

import com.kitpa.kitpaserver.entity.ProblemType;
import lombok.Data;

@Data
public class ProblemDto {
    private Long id;
    private Integer problemNumber;
    private String title;
    private Integer score;
    private Integer solveScore;
    private ProblemType type;
    private String answer;
    private String content;
}
