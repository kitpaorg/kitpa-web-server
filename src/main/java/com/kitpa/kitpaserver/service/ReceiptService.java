package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountExam;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.exception.AlreadyExistsException;
import com.kitpa.kitpaserver.exception.InvalidException;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.repository.AccountExamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    public void receiptExam(String email, Long examId) {
        Account account = accountLookupService.getAccountEntityByEmail(email);
        Exam exam = examService.getExamEntity(examId);

        if (accountExamRepository.existsByAccountAndExam(account, exam)) {
            throw new AlreadyExistsException();
        }

        AccountExam accountExam = AccountExam.create(account, exam);
        accountExamRepository.save(accountExam);
    }

    public Page<ExamDto> getReceiptCanCancellation(Integer page, Integer size, String email) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Account account = accountLookupService.getAccountEntityByEmail(email);
        return accountExamRepository.findByCanCancellation(pageRequest, account)
                .map(AccountExam::getExam)
                .map(e -> mapper.map(e, ExamDto.class));
    }

    @Transactional
    public void cancellationExam(String email, Long examId) {
        Account account = accountLookupService.getAccountEntityByEmail(email);
        Exam examEntity = examService.getExamEntity(examId);
        AccountExam accountExam = accountExamRepository
                .findByAccountAndExam(account, examEntity)
                .orElseThrow(NotFoundException::new);
        if (!accountExam.getExam().canCancel()) {
            throw new InvalidException();
        }
        accountExamRepository.delete(accountExam);
    }
}
