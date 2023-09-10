package com.kitpa.kitpaserver.controller.admin;

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

import static com.kitpa.kitpaserver.utils.PagingUtils.injectPaging;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/problems")
public class AdminProblemController {
    private final ProblemService problemService;

    @GetMapping("/list")
    public String problemListView(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  Model model) {
        Page<ProblemDto> problemDtoPage = problemService.getPagedProblems(page - 1, size);
        model.addAttribute("problems", problemDtoPage);

        injectPaging(model, problemDtoPage);
        return "problem/problem-list";
    }

    @GetMapping("/{id}/detail")
    public String detailView(@PathVariable Long id, Model model) {
        ProblemDto problemDto = problemService.getProblem(id);
        model.addAttribute("problem", problemDto);
        return "problem/problem-detail";
    }

    @GetMapping("/register")
    public String problemRegisterView(@ModelAttribute ProblemForm problemForm, Model model) {
        model.addAttribute("problem", problemForm);
        return "problem/problem-register";
    }


    @PostMapping("/register")
    public String problemRegister(@ModelAttribute ProblemForm form) {
        ProblemDto problemDto = problemService.registerProblem(form);
        return "redirect:/admin/problems/" + problemDto.getId() + "/detail";
    }

    @PostMapping("/{id}/delete")
    public String problemDelete(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return "problem/problem-list";
    }
}
