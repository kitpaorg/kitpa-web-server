package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountUpdateService {
    private final ModelMapper mapper;
    private final AccountLookupService lookupService;
    private final PasswordEncoder passwordEncoder;

    public AccountDto getAccountByUserId(String userId) {
        return lookupService.getAccountByUserId(userId);
    }

    public boolean checkEqualPassword(String userId, String originPass) {
        Account account = lookupService.getAccountEntityByUserId(userId);
        return passwordEncoder.matches(originPass, account.getPassword());
    }

    @Transactional
    public void passwordUpdate(String userId, String newPass) {
        Account account = lookupService.getAccountEntityByUserId(userId);
        String encode = passwordEncoder.encode(newPass);
        account.updatePassword(encode);
    }

    @Transactional
    public void updatePreData(String userId, String selfPhoto, String identityPhoto, Boolean privacyCheck) {
        Account account = lookupService.getAccountEntityByUserId(userId);
        account.updatePreData(selfPhoto, identityPhoto, privacyCheck);
    }
}
