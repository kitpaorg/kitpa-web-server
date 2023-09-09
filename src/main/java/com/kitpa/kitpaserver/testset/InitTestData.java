package com.kitpa.kitpaserver.testset;

import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.entity.ProblemType;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.form.ExamForm;
import com.kitpa.kitpaserver.form.ProblemForm;
import com.kitpa.kitpaserver.service.AccountRegisterService;
import com.kitpa.kitpaserver.service.ExamService;
import com.kitpa.kitpaserver.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InitTestData {
    private final AccountRegisterService accountRegisterService;
    private final ExamService examService;
    private final ProblemService problemService;

    @PostConstruct
    public void initAccount(){
        ProblemForm problemForm1 = new ProblemForm();
        problemForm1.setProblemNumber(1);
        problemForm1.setAnswer("5");
        problemForm1.setType(ProblemType.MULTIPLE);
        problemForm1.setTitle("문제1번");
        problemForm1.setContent("# 문제1번 내용입니다.");
        problemForm1.setMaxScore(5);

        ProblemForm problemForm2 = new ProblemForm();
        problemForm2.setProblemNumber(2);
        problemForm2.setAnswer("public");
        problemForm2.setType(ProblemType.BLANK);
        problemForm2.setTitle("문제2번");
        problemForm2.setContent("# 문제2번 내용입니다.");
        problemForm2.setMaxScore(20);

        ProblemForm problemForm3 = new ProblemForm();
        problemForm3.setProblemNumber(3);
        problemForm3.setAnswer("println(\"hello\");");
        problemForm3.setType(ProblemType.SHORT);
        problemForm3.setTitle("문제3번");
        problemForm3.setContent("# 문제3번 내용입니다.");
        problemForm3.setMaxScore(10);
        problemService.registerProblem(problemForm1);
        problemService.registerProblem(problemForm2);
        problemService.registerProblem(problemForm3);

        ExamForm examForm = new ExamForm();
        examForm.setTitle("이번 대회의 제목입니다.");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receiptIdleDate = now.minusMinutes(1L);
        examForm.setReceiptIdleDate(receiptIdleDate);
        examForm.setStartDate(now.plusMinutes(1L));
        examForm.setEndDate(now.plusHours(2L));
        examForm.setProblemIds(List.of(1L,2L,3L));

        examService.registerExam(examForm);

        AccountForm accountForm1 = new AccountForm();
        accountForm1.setEmail("iro@gmail.com");
        accountForm1.setRealName("iro");
        accountForm1.setPhoneNumber("01012341234");
        accountForm1.setUserId("iro");
        accountForm1.setExam(1L);

        AccountForm accountForm2 = new AccountForm();
        accountForm2.setEmail("kayoko@gmail.com");
        accountForm2.setRealName("kayoko");
        accountForm2.setPhoneNumber("01012341234");
        accountForm2.setUserId("kayoko");
        accountForm2.setExam(1L);

        accountRegisterService.createAccount(accountForm1);
        accountRegisterService.createAccount(accountForm2);
    }
}
