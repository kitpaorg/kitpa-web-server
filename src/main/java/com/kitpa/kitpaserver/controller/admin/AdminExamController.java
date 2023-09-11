package com.kitpa.kitpaserver.controller.admin;

import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.form.ExamForm;
import com.kitpa.kitpaserver.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.kitpa.kitpaserver.utils.PagingUtils.injectPaging;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/exams")
public class AdminExamController {
    private final ExamService examService;

    @GetMapping("/list")
    public String examListView(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer size,
                               Model model) {
        Page<ExamDto> pagedExam = examService.getPagedExam(page - 1, size);
        model.addAttribute("exams", pagedExam);
        injectPaging(model, pagedExam);
        return "exam/exam-list";
    }

    @GetMapping("/{id}/detail")
    public String examDetailView(@PathVariable Long id, Model model) {
        ExamDto examDto = examService.getExam(id);
        model.addAttribute("exam", examDto);
        return "exam/exam-detail";
    }

    @GetMapping("/register")
    public String examRegisterView(@ModelAttribute ExamForm examForm, Model model) {
        model.addAttribute("exam", examForm);
        return "exam/exam-register";
    }

    @PostMapping("/register")
    public String examRegister(@ModelAttribute ExamForm examForm) {
        ExamDto examDto = examService.registerExam(examForm);
        return "redirect:/admin/exams/"+examDto.getId()+"/detail";
    }

    @PostMapping("/{id}/delete")
    public String examDelete(@PathVariable Long id) {
        examService.deleteExam(id);
        return "exam/exam-list";
    }
}
