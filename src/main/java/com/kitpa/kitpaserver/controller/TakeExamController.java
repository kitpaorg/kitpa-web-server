package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.annotation.CurrentUserId;
import com.kitpa.kitpaserver.dto.ProblemDeployDto;
import com.kitpa.kitpaserver.dto.SolveDto;
import com.kitpa.kitpaserver.entity.Exam;
import com.kitpa.kitpaserver.entity.Problem;
import com.kitpa.kitpaserver.entity.ProblemType;
import com.kitpa.kitpaserver.form.TakeExamPreForm;
import com.kitpa.kitpaserver.service.TakeExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//TODO: 재입장 고려할 것
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/take")
public class TakeExamController {
    private final TakeExamService takeExamService;

    @GetMapping("/exam")
    public String takeExamView(@CurrentUserId String userId, @RequestParam Long problemNumber, Model model) {
        if(!takeExamService.canEnterExam(userId)){
            return "take-exam/cannot-enter-exam";
        }

        ProblemDeployDto problem = takeExamService.examDeploy(userId, problemNumber);
        Exam exam = takeExamService.getExamByAccount(userId);
        List<Integer> problemNumberList = exam.getProblems().stream()
                .map(Problem::getProblemNumber)
                .sorted()
                .collect(Collectors.toList());

        Integer firstNumber = problemNumberList.get(0);
        Integer lastNumber = problemNumberList.get(problemNumberList.size() - 1);

        model.addAttribute("problem", problem);
        model.addAttribute("problemNumber", problem.getProblemNumber());
        model.addAttribute("problemNumberList", problemNumberList);
        model.addAttribute("endDate", exam.getEndDate());
        model.addAttribute("isFirst", problem.getProblemNumber().equals(firstNumber));
        model.addAttribute("isLast", problem.getProblemNumber().equals(lastNumber));
        return "take-exam/take-exam";
    }

    @GetMapping("/exam/test")
    public String takeExamView(Model model) {
        ProblemDeployDto problem = new ProblemDeployDto();
        problem.setProblemNumber(1);
        problem.setId(1L);
        problem.setType(ProblemType.CODE);
        problem.setTitle("hello world");
        problem.setContent("# blabla  ~~brabra~~");
        problem.setMaxScore(5);

        model.addAttribute("problem", problem);
        model.addAttribute("problemNumber", 1);
        model.addAttribute("problemNumberList", Arrays.asList(1,2,3,4,5,6,7,8));
        model.addAttribute("endDate", LocalDateTime.of(2023, 9, 3, 22, 0, 0));
        model.addAttribute("isFirst", problem.getProblemNumber().equals(1));
        model.addAttribute("isLast", problem.getProblemNumber().equals(5));
        return "take-exam/take-exam";
    }

    @PostMapping("/exam")
    public String takeExam(@CurrentUserId String userId, @ModelAttribute SolveDto solveDto, RedirectAttributes redirectAttributes){
        //TODO: 마지막 문제인지 확인해서 마지막 문제면 제출 완료 로직으로 빠져야함
        //TODO: 사용자가 입력한 답을 저장하는 로직 작성
        if(!takeExamService.canSubmit(userId)){
            return "redirect:/take/exam-finish";
        }

        if (solveDto.getDirection().equals("next")) {
            redirectAttributes.addAttribute("problemNumber", solveDto.getProblemNumber() + 1);
        }else if(solveDto.getDirection().equals("prev")){
            redirectAttributes.addAttribute("problemNumber", solveDto.getProblemNumber() - 1);
        }
        return "redirect:/take/exam";
    }

    @GetMapping("/exam-finish")
    public String examFinishView(){
        return "take-exam/exam-finish";
    }

    @GetMapping("/share-screen")
    public String shareMonitorView() {
        return "take-exam/share-screen";
    }

    @GetMapping("/take-exam-pre")
    public String takeExamPreView(@CurrentUserId String userId) {
        if (!takeExamService.canEnterIdle(userId)) {
            return "take-exam/cannot-enter-idle";
        }
        return "take-exam/take-exam-pre";
    }

    @PostMapping("/take-exam-pre")
    public String takeExamPre(@CurrentUserId String userId,
                              @ModelAttribute TakeExamPreForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "시험 전 제출 정보 오류");
            return "redirect:/take/take-exam-pre";
        }
        takeExamService.registerPreData(userId, form);
        return "redirect:/take/exam";
    }
}
