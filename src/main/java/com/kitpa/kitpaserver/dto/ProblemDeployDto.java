package com.kitpa.kitpaserver.dto;

import com.kitpa.kitpaserver.entity.ProblemType;
import lombok.Data;

@Data
public class ProblemDeployDto {
    private Long id;
    private Integer problemNumber;
    private String title;
    private Integer maxScore;
    private ProblemType type;
    private String content;
}
