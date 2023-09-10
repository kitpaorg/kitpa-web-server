package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.ProblemDeployDto;
import com.kitpa.kitpaserver.dto.SolveDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountProblem;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.form.TakeExamPreForm;
import com.kitpa.kitpaserver.repository.AccountProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TakeExamService {
    @Value("${save.path}")
    private String savePath;
    private final AccountUpdateService accountUpdateService;
    private final AccountLookupService accountLookupService;
    private final ExamService examService;
    private final ProblemService problemService;
    private final ModelMapper mapper;
    private final AccountProblemRepository accountProblemRepository;

    @Transactional
    public void registerPreData(String userId, TakeExamPreForm form) {
        String savedSelfPhotoFileName = saveFile(form.getSelfPhoto());
        String savedIdentityCardPhotoFileName = saveFile(form.getIdentityCardPhoto());
        accountUpdateService.updatePreData(
                userId,
                savedSelfPhotoFileName,
                savedIdentityCardPhotoFileName,
                form.getPrivacyCheck());
    }

    private String saveFile(MultipartFile selfPhoto) {
        String ext = parseExt(selfPhoto.getOriginalFilename());
        savePath = savePath.substring(savePath.length() - 1).equals("/") ? savePath : savePath + "/";
        String saveFileName = selfPhoto.getName() + "-" + UUID.randomUUID() + "." + ext;
        File file = new File(savePath + saveFileName);
        try {
            selfPhoto.transferTo(file);
            return saveFileName;
        } catch (IOException e) {
            log.error("cannot save self photo, e=", e);
            throw new RuntimeException(e);
        }
    }

    private String parseExt(String fullFileName) {
        return StringUtils.substringAfterLast(
                fullFileName, ".");
    }

    public boolean canEnterExam(String userId) {
        Exam exam = findExamByAccount(userId);
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        return !account.getFinishExam() && account.isAdditionalWrite() && exam.canEnterExam(LocalDateTime.now());
    }

    public boolean canEnterIdle(String userId) {
        Exam exam = findExamByAccount(userId);
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        return !account.isAdditionalWrite() && !account.getFinishExam() && exam.canEnterIdle(now);
    }

    private Exam findExamByAccount(String userId) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        Long examId = account.getExam();
        return examService.getExamEntity(examId);
    }

    public ProblemDeployDto examDeploy(String userId, Integer problemNumber) {
        Exam exam = findExamByAccount(userId);
        Problem problem = problemService.getProblemByExamAndProblemNumber(exam, problemNumber);
        return mapper.map(problem, ProblemDeployDto.class);
    }

    public List<Integer> problemNumberList(String userId, Long problemNumber) {
        Exam exam = findExamByAccount(userId);
        return exam.getProblems().stream()
                .map(Problem::getProblemNumber)
                .sorted()
                .collect(Collectors.toList());
    }

    public Exam getExamByAccount(String userId) {
        return findExamByAccount(userId);
    }

    public boolean canSubmit(String userId) {
        LocalDateTime now = LocalDateTime.now();
        Exam exam = findExamByAccount(userId);

        //제출시마다 시간 및 계정 valid 해야됨
        return !exam.isEndAfter(now);
    }
    @Transactional
    public void submitFinish(String userId, SolveDto solveDto) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        AccountProblem accountProblem = accountProblemRepository.findByUserIdAndProblemNumber(userId, solveDto.getProblemNumber())
                .orElseGet(() -> AccountProblem.create(solveDto.getProblemNumber(), solveDto.getAnswer(), account));
        accountProblem.setAnswer(solveDto.getAnswer());
        account.setFinishExam(true);
        accountProblemRepository.save(accountProblem);
    }

    @Transactional
    public void submit(String userId, SolveDto solveDto) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        AccountProblem accountProblem = accountProblemRepository.findByUserIdAndProblemNumber(userId, solveDto.getProblemNumber())
                .orElseGet(() -> AccountProblem.create(solveDto.getProblemNumber(), solveDto.getAnswer(), account));
        accountProblem.setAnswer(solveDto.getAnswer());

        accountProblemRepository.save(accountProblem);
    }

    public String getAnswerIfExists(String userId, Integer problemNumber) {
        AccountProblem accountProblem = accountProblemRepository.findByUserIdAndProblemNumber(userId, problemNumber)
                .orElse(null);
        if (accountProblem != null) {
            return accountProblem.getAnswer();
        }
        return null;
    }

    public boolean canReEnter(String userId) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        Exam exam = findExamByAccount(userId);
        LocalDateTime now = LocalDateTime.now();
        return account.isAdditionalWrite() && exam.canReEnter(now);
    }

    public boolean isFinishedAccount(String userId) {
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        return account.getFinishExam();
    }
}
