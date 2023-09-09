package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.ProblemDto;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.form.ProblemForm;
import com.kitpa.kitpaserver.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProblemService {
    private final ModelMapper mapper;
    private final ProblemRepository repository;

    @Transactional
    public ProblemDto registerProblem(ProblemForm form) {
        Problem problem = form.toEntity();
        Problem save = repository.save(problem);
        return mapper.map(save, ProblemDto.class);
    }

    @Transactional
    public void deleteProblem(Long id) {
        Problem problem = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        repository.delete(problem);
    }

    public Page<ProblemDto> getPagedProblems(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return repository.findPageBy(pageRequest)
                .map(p -> mapper.map(p, ProblemDto.class));
    }

    public ProblemDto getProblem(Long id) {
        Problem problem = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapper.map(problem, ProblemDto.class);
    }

    public Problem getProblemByExamAndProblemNumber(Exam exam, Long problemNumber) {
        return repository.findProblemByExamAndProblemNumber(exam, problemNumber)
                .orElseThrow(NotFoundException::new);
    }
}
