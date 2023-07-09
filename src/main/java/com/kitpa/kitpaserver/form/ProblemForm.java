package com.kitpa.kitpaserver.form;

import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.entity.ProblemType;
import lombok.Data;

@Data
public class ProblemForm {
    private Integer problemNumber;
    private String title;
    private Integer score;
    private ProblemType type;
    private String answer;
    private String content;

    public Problem toEntity(){
        return Problem.builder()
                .problemNumber(problemNumber)
                .title(title)
                .type(type)
                .maxScore(score)
                .answer(answer)
                .content(content)
                .build();
    }
}
