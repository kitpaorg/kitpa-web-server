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
//@Component
public class InitTestData {
    private final AccountRegisterService accountRegisterService;
    private final ExamService examService;
    private final ProblemService problemService;

//    @PostConstruct
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

        ProblemForm problemForm4 = new ProblemForm();
        problemForm4.setProblemNumber(4);
        problemForm4.setAnswer("println(\"hello\");");
        problemForm4.setType(ProblemType.SHORT);
        problemForm4.setTitle("문제4번");
        problemForm4.setContent("# 문제4번 내용입니다.");
        problemForm4.setMaxScore(10);
        ProblemForm problemForm5 = new ProblemForm();
        problemForm5.setProblemNumber(5);
        problemForm5.setAnswer("println(\"hello\");");
        problemForm5.setType(ProblemType.SHORT);
        problemForm5.setTitle("문제5번");
        problemForm5.setContent("# 문제5번 내용입니다.");
        problemForm5.setMaxScore(10);
        ProblemForm problemForm6 = new ProblemForm();
        problemForm6.setProblemNumber(6);
        problemForm6.setAnswer("println(\"hello\");");
        problemForm6.setType(ProblemType.SHORT);
        problemForm6.setTitle("문제6번");
        problemForm6.setContent("# 문제6번 내용입니다.");
        problemForm6.setMaxScore(10);
        ProblemForm problemForm7 = new ProblemForm();
        problemForm7.setProblemNumber(7);
        problemForm7.setAnswer("println(\"hello\");");
        problemForm7.setType(ProblemType.SHORT);
        problemForm7.setTitle("문제37번");
        problemForm7.setContent("# 문제7번 내용입니다.");
        problemForm7.setMaxScore(10);
        ProblemForm problemForm8 = new ProblemForm();
        problemForm8.setProblemNumber(8);
        problemForm8.setAnswer("println(\"hello\");");
        problemForm8.setType(ProblemType.SHORT);
        problemForm8.setTitle("문제8번");
        problemForm8.setContent("# 문제8번 내용입니다.");
        problemForm8.setMaxScore(10);
        ProblemForm problemForm9 = new ProblemForm();
        problemForm9.setProblemNumber(9);
        problemForm9.setAnswer("println(\"hello\");");
        problemForm9.setType(ProblemType.SHORT);
        problemForm9.setTitle("문제9번");
        problemForm9.setContent("# 문제9번 내용입니다.");
        problemForm9.setMaxScore(10);
        ProblemForm problemForm10 = new ProblemForm();
        problemForm10.setProblemNumber(10);
        problemForm10.setAnswer("println(\"hello\");");
        problemForm10.setType(ProblemType.SHORT);
        problemForm10.setTitle("문제10번");
        problemForm10.setContent("# 문제10번 내용입니다.");
        problemForm10.setMaxScore(10);
        ProblemForm problemForm11 = new ProblemForm();
        problemForm11.setProblemNumber(11);
        problemForm11.setAnswer("println(\"hello\");");
        problemForm11.setType(ProblemType.SHORT);
        problemForm11.setTitle("문제11번");
        problemForm11.setContent("# 문제11번 내용입니다.");
        problemForm11.setMaxScore(10);
        ProblemForm problemForm12 = new ProblemForm();
        problemForm12.setProblemNumber(12);
        problemForm12.setAnswer("println(\"hello\");");
        problemForm12.setType(ProblemType.SHORT);
        problemForm12.setTitle("문제12번");
        problemForm12.setContent("# 문제12번 내용입니다.");
        problemForm12.setMaxScore(10);
        ProblemForm problemForm13 = new ProblemForm();
        problemForm13.setProblemNumber(13);
        problemForm13.setAnswer("println(\"hello\");");
        problemForm13.setType(ProblemType.SHORT);
        problemForm13.setTitle("문제13번");
        problemForm13.setContent("# 문제13번 내용입니다.");
        problemForm13.setMaxScore(10);
        ProblemForm problemForm14 = new ProblemForm();
        problemForm14.setProblemNumber(14);
        problemForm14.setAnswer("println(\"hello\");");
        problemForm14.setType(ProblemType.SHORT);
        problemForm14.setTitle("문제14번");
        problemForm14.setContent("# 문제14번 내용입니다.");
        problemForm14.setMaxScore(10);

        problemService.registerProblem(problemForm1);
        problemService.registerProblem(problemForm2);
        problemService.registerProblem(problemForm3);
        problemService.registerProblem(problemForm4);
        problemService.registerProblem(problemForm5);
        problemService.registerProblem(problemForm6);
        problemService.registerProblem(problemForm7);
        problemService.registerProblem(problemForm8);
        problemService.registerProblem(problemForm9);
        problemService.registerProblem(problemForm10);
        problemService.registerProblem(problemForm11);
        problemService.registerProblem(problemForm12);
        problemService.registerProblem(problemForm13);
        problemService.registerProblem(problemForm14);

        ExamForm examForm = new ExamForm();
        examForm.setTitle("이번 대회의 제목입니다.");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receiptIdleDate = now.minusMinutes(1L);
        examForm.setReceiptIdleDate(receiptIdleDate);
        examForm.setStartDate(now.plusSeconds(10L));
        examForm.setEndDate(now.plusHours(2L));
        examForm.setProblemIds(List.of(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L,11L,12L,13L,14L));

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
