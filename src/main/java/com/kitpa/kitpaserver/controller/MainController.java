package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.annotation.CurrentUserId;
import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.service.AccountLookupService;
import com.kitpa.kitpaserver.service.ExamService;
import com.kitpa.kitpaserver.service.TakeExamService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final AccountLookupService accountLookupService;
    private final ExamService examService;

    @RequestMapping("/")
    public String mainView(@CurrentUserId String userId, Model model) {
        if (!StringUtils.isEmpty(userId)) {
            AccountDto accountByUserId = accountLookupService.getAccountByUserId(userId);
            ExamDto exam = examService.getExam(accountByUserId.getExam());
            model.addAttribute("account", accountByUserId);
            model.addAttribute("exam", exam);
        }

        return "main/index";
    }
}
