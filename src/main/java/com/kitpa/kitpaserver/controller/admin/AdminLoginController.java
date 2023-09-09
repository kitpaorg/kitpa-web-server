package com.kitpa.kitpaserver.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/accounts")
public class AdminLoginController {
    @GetMapping("/login")
    public String adminLoginView() {
        return "admin-login";
    }
}
