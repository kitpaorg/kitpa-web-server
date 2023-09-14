package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.annotation.CurrentUserId;
import com.kitpa.kitpaserver.dto.ProblemDeployDto;
import com.kitpa.kitpaserver.dto.SolveDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.form.TakeExamPreForm;
import com.kitpa.kitpaserver.service.AccountLookupService;
import com.kitpa.kitpaserver.service.TakeExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/take")
public class TakeExamController {
    private final TakeExamService takeExamService;
    private final AccountLookupService accountLookupService;

    @GetMapping("/exam")
    public String takeExamView(@CurrentUserId String userId, @RequestParam Integer problemNumber, Model model) {
        if (!takeExamService.canEnterExam(userId)) {
            return "error/4xx";
        }

        ProblemDeployDto problem = takeExamService.examDeploy(userId, problemNumber);
        Exam exam = takeExamService.getExamByAccount(userId);
        List<Integer> problemNumberList = exam.getProblems().stream()
                .map(Problem::getProblemNumber)
                .sorted()
                .collect(Collectors.toList());

        String answer = takeExamService.getAnswerIfExists(userId, problemNumber);

        Integer firstNumber = problemNumberList.get(0);
        Integer lastNumber = problemNumberList.get(problemNumberList.size() - 1);

        if (StringUtils.isNotBlank(answer)) {
            model.addAttribute("answer", answer);
        }

        model.addAttribute("problem", problem);
        model.addAttribute("problemNumber", problem.getProblemNumber());
        model.addAttribute("problemNumberList", problemNumberList);
        model.addAttribute("endDate", exam.getEndDate());
        model.addAttribute("isFirst", problem.getProblemNumber().equals(firstNumber));
        model.addAttribute("isLast", problem.getProblemNumber().equals(lastNumber));
        return "take-exam/take-exam";
    }

    @PostMapping("/exam")
    public String takeExam(@CurrentUserId String userId, @ModelAttribute SolveDto solveDto, RedirectAttributes redirectAttributes) {
        if (!takeExamService.canSubmit(userId)) {
            return "redirect:/take/exam-finish";
        }

        takeExamService.submit(userId, solveDto);

        if (solveDto.getSelectNumber() != null) {
            redirectAttributes.addAttribute("problemNumber", solveDto.getSelectNumber());
            return "redirect:/take/exam";
        }

        if (solveDto.getDirection() != null) {
            if (solveDto.getDirection().equals("next")) {
                redirectAttributes.addAttribute("problemNumber", solveDto.getProblemNumber() + 1);
            } else if (solveDto.getDirection().equals("prev")) {
                redirectAttributes.addAttribute("problemNumber", solveDto.getProblemNumber() - 1);
            }
        }

        return "redirect:/take/exam";
    }

    @PostMapping("/exam/submit")
    public String submitExam(@CurrentUserId String userId, @ModelAttribute SolveDto solveDto) {
        if (!takeExamService.canSubmit(userId)) {
            return "redirect:/take/exam-finish";
        }

        takeExamService.submitFinish(userId, solveDto);

        return "redirect:/take/exam-finish";
    }

    @GetMapping("/exam-finish")
    public String examFinishView() {
        return "take-exam/exam-finish";
    }

    @GetMapping("/share-screen")
    public String shareMonitorView() {
        return "take-exam/share-screen";
    }

    @GetMapping("/take-exam-pre")
    public String takeExamPreView(@CurrentUserId String userId, Model model) {
        Exam exam = takeExamService.getExamByAccount(userId);
        Account account = accountLookupService.getAccountEntityByUserId(userId);
        LocalDateTime now = LocalDateTime.now();
        //시험 시간이 끝나지 않았을 때
        if (!exam.isEndAfter(now)) {
            //시험 시작 후 일 때
            if (exam.isStarted(now)) {
                //사전 정보를 다 입력 했을 때
                if (account.isAdditionalWrite()) {
                    //시험을 마친 상태일 때
                    if (account.getFinishExam()) {
                        //이미 제출 완료한 시험입니다.
                        return "error/already-finish";
                    }
                    //시험을 마치지 않은 상태일 때
                    else {
                        //재입장 처리
                        return "redirect:/take/exam?problemNumber=1";
                    }
                }
                //사전 정보를 입력하지 않았을 때
                else {
                    //이미 시작된 시험입니다.
                    return "error/already-started";
                }
            }
            //시험 시작 전 일 때
            else {
                //시험 준비 시간 후 일 때
                if (exam.isReadyAfter(now)) {
                    TakeExamPreForm takeExamPreForm = new TakeExamPreForm();
                    model.addAttribute("startDate", exam.getStartDate());
                    model.addAttribute("form", takeExamPreForm);
                    return "take-exam/take-exam-pre";
                }
                //시험 준비 시간 전 일 때
                else {
                    return "error/cannot-enter-idle";
                }
            }
        }
        //시험 시간이 끝난 후 일 때
        else{
            return "error/not-exists-exam";
        }
        //시험 시작 전 일때
        //시험 준비 시간 후 일 때
        //입장 가능
        //시험 준비 시간 전 일 때
        //입장 불가
        //시험 시작 후 일때
        //사전 정보 다 입력했을 때
        //시험 마치지 않았을 때
        //take/exam 으로 재입장 처리
        //시험 마쳤을 때
        //입장 불가
        //사전 정보 하나라도 입력안했을 때
        //입장 불가
    }

    @PostMapping("/take-exam-pre")
    public String takeExamPre(@CurrentUserId String userId,
                              @ModelAttribute("form") TakeExamPreForm form,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "시험 전 제출 정보 오류");
            return "redirect:/take/take-exam-pre";
        }
        takeExamService.registerPreData(userId, form);
        Exam exam = takeExamService.getExamByAccount(userId);
        LocalDateTime now = LocalDateTime.now();
        if (exam.isStarted(now)) {
            return "redirect:/take/exam?problemNumber=1";
        }else{
            model.addAttribute("error", "시험 시작 시간 전입니다.");
            model.addAttribute("enterActive", true);
            return "take-exam/take-exam-pre";
        }
    }
}
