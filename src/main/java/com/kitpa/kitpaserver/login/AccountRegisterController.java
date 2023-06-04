package com.kitpa.kitpaserver.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account/register")
public class AccountRegisterController {
    private final AccountRegisterService registerService;

    @GetMapping
    public String getAccountRegisterView() {
        return "register";
    }

    @PostMapping
    public String doAccountRegister(@ModelAttribute LoginForm form) {
        registerService.createAccount(form);
        return "redirect:/login";
    }
}
