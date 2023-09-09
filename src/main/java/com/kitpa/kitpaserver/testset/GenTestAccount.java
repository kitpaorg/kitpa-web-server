package com.kitpa.kitpaserver.testset;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.AdminAccount;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.repository.AdminAccountRepository;
import com.kitpa.kitpaserver.service.AccountRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
public class GenTestAccount {
    private final AccountRegisterService accountRegisterService;

    @PostConstruct
    public void initAccount(){
        AccountForm accountForm1 = new AccountForm();
        accountForm1.setEmail("iro@gmail.com");
        accountForm1.setRealName("iro");
        accountForm1.setPhoneNumber("01012341234");
        accountForm1.setUserId("iro");

        AccountForm accountForm2 = new AccountForm();
        accountForm2.setEmail("kayoko@gmail.com");
        accountForm2.setRealName("kayoko");
        accountForm2.setPhoneNumber("01012341234");
        accountForm2.setUserId("kayoko");

        accountRegisterService.createAccount(accountForm1);
        accountRegisterService.createAccount(accountForm2);
    }
}
