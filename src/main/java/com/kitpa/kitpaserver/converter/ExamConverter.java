package com.kitpa.kitpaserver.converter;

import com.kitpa.kitpaserver.dto.ProblemDto;
import com.kitpa.kitpaserver.entity.Problem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ExamConverter extends AbstractConverter<List<Problem>, List<ProblemDto>> {
    private final ModelMapper mapper;
    @Override
    protected List<ProblemDto> convert(List<Problem> problems) {
        return problems.stream()
                .map(p -> {
                    ProblemDto map = mapper.map(p, ProblemDto.class);
                    map.setAssigned(p.getExam() != null);
                    return map;
                })
                .collect(Collectors.toList());
    }
}
