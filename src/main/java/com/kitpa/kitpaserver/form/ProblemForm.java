package com.kitpa.kitpaserver.form;

import com.kitpa.kitpaserver.entity.ProblemType;
import lombok.Data;

@Data
public class ProblemForm {
    private String title;
    private Integer score;
    private Integer solveScore;
    private ProblemType type;
    private String answer;
    private String content;
}
