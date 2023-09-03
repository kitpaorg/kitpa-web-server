package com.kitpa.kitpaserver.dto;

import lombok.Data;

@Data
public class SolveDto {
    private String answer;
    private Integer problemNumber;
    private String direction;
}
