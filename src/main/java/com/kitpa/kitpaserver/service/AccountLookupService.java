package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountLookupService {
    private final AccountRepository accountRepository;

    public Account getAccountByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new NotFoundException();
        }
        return accountRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }
}
