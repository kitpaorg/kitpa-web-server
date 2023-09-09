package com.kitpa.kitpaserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class LoginController {
    @GetMapping("/login")
    public String accountLoginView() {
        return "account/login";
    }
}
