package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.converter.ExamConverter;
import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.form.ExamForm;
import com.kitpa.kitpaserver.repository.ExamRepository;
import com.kitpa.kitpaserver.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {
    private final ModelMapper mapper;
    private final ExamRepository examRepository;
    private final ProblemRepository problemRepository;
    private final ExamConverter examConverter;

    @Transactional
    public ExamDto registerExam(ExamForm examForm) {
        Exam exam = mapToExam(examForm);
        Exam save = examRepository.save(exam);
        return mapToExamDto(save);
    }

    private ExamDto mapToExamDto(Exam save) {
        return mapper.typeMap(Exam.class, ExamDto.class)
                .addMappings(m -> m.using(examConverter))
                .map(save);
    }

    private Exam mapToExam(ExamForm form) {
        List<Problem> problems = null;
        if (form.hasProblems()) {
            problems = problemRepository
                    .findByIdIn(form.getProblemIds());
        }
        return Exam.create(
                form.getTitle(),
                form.getReceiptIdleDate(),
                form.getStartDate(),
                form.getEndDate(),
                problems);
    }

    @Transactional
    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        examRepository.delete(exam);
    }

    public Page<ExamDto> getPagedExam(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return examRepository.findPageBy(pageRequest)
                .map(this::mapToExamDto);
    }

    public ExamDto getExam(Long id) {
        return examRepository.findWithRelationById(id)
                .map(this::mapToExamDto)
                .orElseThrow(NotFoundException::new);
    }

    public Exam getExamEntity(Long id) {
        return examRepository.findWithRelationById(id)
                .orElseThrow(NotFoundException::new);
    }

//    public Page<ExamDto> getPagedExamWhenCanReceipt(Integer page, Integer size){
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return examRepository.findPageByCanReceipt(pageRequest, LocalDateTime.now())
//                .map(this::mapToExamDto);
//    }
}
