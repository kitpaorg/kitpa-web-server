package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.annotation.CurrentUserId;
import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.service.AccountRegisterService;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.service.AccountUpdateService;
import com.kitpa.kitpaserver.utils.AccountCSVParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRegisterService registerService;
    private final AccountUpdateService updateService;

    @GetMapping("/login")
    public String accountLoginView() {
        return "account/login";
    }

    @GetMapping("/register")
    public String accountRegisterView() {
        return "account/register";
    }

    @PostMapping("/register")
    public String accountRegister(@ModelAttribute AccountForm form) {
        registerService.createAccount(form);
        return "account/register-after-check";
    }

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

    @GetMapping("/config")
    public String accountConfigView(@CurrentUserId String userId, Model model) {
        AccountDto accountDto = updateService.getAccountByUserId(userId);
        model.addAttribute("account", accountDto);
        return "account/config";
    }

    @PostMapping("/config")
    public String accountConfig(@CurrentUserId String email,
                                @ModelAttribute AccountForm form,
                                RedirectAttributes redirectAttributes){
        if(StringUtils.isEmpty(form.getEmail())){
            redirectAttributes.addFlashAttribute("error", "잘못된 요청");
            return "redirect:/account/config";
        }

        if (!form.getEmail().equals(email)) {
            redirectAttributes.addFlashAttribute("error", "잘못된 요청");
            return "redirect:/account/config";
        }

        if (form.isUpdatePassword()) {
            if (!updateService.checkEqualPassword(form.getUserId(), form.getOriginPass())) {
                redirectAttributes.addFlashAttribute("error", "패스워드 불일치");
                return "redirect:/account/config";
            }
            updateService.passwordUpdate(form.getUserId(), form.getNewPass());
        }

        //TODO: pass 말고 다른 정보 update
        redirectAttributes.addFlashAttribute("result", "변경 완료");
        return "redirect:/account/config";
    }
}
