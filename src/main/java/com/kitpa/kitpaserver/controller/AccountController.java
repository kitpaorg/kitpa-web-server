package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.service.AccountRegisterService;
import com.kitpa.kitpaserver.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountRegisterService registerService;

    @GetMapping("/login")
    public String accountLoginView() {
        return "account/login";
    }

    @GetMapping("/register")
    public String accountRegisterView() {
        return "account/register";
    }

    @PostMapping("/register")
    public String accountRegister(@ModelAttribute LoginForm form) {
        registerService.createAccount(form);
        return "account/register-after-check";
    }
}
