package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountLookupService {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    public AccountDto getAccountByEmail(String email){
        Account account = getAccount(email);
        return mapper.map(account, AccountDto.class);
    }

    @Transactional
    public Account getAccountEntityByEmail(String email) {
        return getAccount(email);
    }

    private Account getAccount(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new NotFoundException();
        }
        return accountRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }
}
