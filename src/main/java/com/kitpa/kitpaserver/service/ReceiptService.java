package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountExam;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.exception.AlreadyExistsException;
import com.kitpa.kitpaserver.repository.AccountExamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final ModelMapper mapper;
    private final ExamService examService;
    private final AccountExamRepository accountExamRepository;
    private final AccountLookupService accountLookupService;

    public Page<ExamDto> getPagedExamWhenCanReceipt(Integer page, Integer size) {
        return examService.getPagedExamWhenCanReceipt(page, size);
    }

    public void receiptExam(String userId, Long examId) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        Exam exam = examService.getExamEntity(examId);

        if (accountExamRepository.existsByAccountAndExam(account, exam)) {
            throw new AlreadyExistsException();
        }

        AccountExam accountExam = AccountExam.create(account, exam);
        accountExamRepository.save(accountExam);
    }
}
