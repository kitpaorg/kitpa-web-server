package com.kitpa.kitpaserver.controller.admin;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.service.AccountRegisterService;
import com.kitpa.kitpaserver.service.AccountUpdateService;
import com.kitpa.kitpaserver.utils.AccountCSVParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    private final AccountRegisterService registerService;

    @GetMapping("/bulk-register")
    public String accountBulkRegisterView() {
        return "account/bulk-import-account";
    }

    @PostMapping("/bulk-register")
    public String bulkRegister(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        List<AccountForm> accounts = AccountCSVParser.parse(multipartFile);
        List<AccountDto> accountDtos = registerService.createAccount(accounts);
        model.addAttribute("accounts", accountDtos);
        return "account/bulk-import-result";
    }
}
