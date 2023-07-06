package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.dto.ProblemDto;
import com.kitpa.kitpaserver.form.ProblemForm;
import com.kitpa.kitpaserver.service.ProblemService;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/problems")
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/list")
    public String problemListView(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  Model model) {
        Page<ProblemDto> problemDtoPage = problemService.getPagedProblems(page - 1, size);
        model.addAttribute("problems", problemDtoPage);

        int totalPages = problemDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "problem/problem-list";
    }

    @GetMapping("/register")
    public String problemRegisterView(@ModelAttribute ProblemForm problemForm, Model model) {
        model.addAttribute("problem", problemForm);
        return "problem/problem-register";
    }

    @PostMapping("/register")
    public String problemRegister(@ModelAttribute ProblemForm form, Model model) {
        ProblemDto problemDto = problemService.registerProblem(form);
        model.addAttribute("problem", problemDto);
        return "problem/problem-detail";
    }

    @PostMapping("/{id}/delete")
    public String problemDelete(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return "problem/problem-list";
    }
}
