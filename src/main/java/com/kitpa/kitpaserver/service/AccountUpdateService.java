package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountUpdateService {
    private final AccountLookupService lookupService;
    private final PasswordEncoder passwordEncoder;

    public AccountDto getAccountByEmail(String email) {
        return lookupService.getAccountByEmail(email);
    }

    public boolean checkEqualPassword(String email, String originPass) {
        Account account = lookupService.getAccountEntityByEmail(email);
        return passwordEncoder.matches(originPass, account.getPassword());
    }

    @Transactional
    public void passwordUpdate(String email, String newPass) {
        Account account = lookupService.getAccountEntityByEmail(email);
        String encode = passwordEncoder.encode(newPass);
        account.updatePassword(encode);
    }
}
