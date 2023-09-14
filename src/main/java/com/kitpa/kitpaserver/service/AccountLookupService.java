package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.exception.NotFoundException;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountLookupService {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    public AccountDto getAccountByUserId(String userId){
        Account account = getAccount(userId);
        return mapper.map(account, AccountDto.class);
    }

    @Transactional
    public Account getAccountEntityByUserId(String userId) {
        return getAccount(userId);
    }

    private Account getAccount(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new NotFoundException();
        }
        return accountRepository.findByUserId(userId)
                .orElseThrow(NotFoundException::new);
    }

    public Page<AccountDto> getPagedAccounts(int page, Integer size, String phoneNumber) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return accountRepository.findPageByPhoneNumberContains(pageRequest, phoneNumber)
                .map(a->mapper.map(a, AccountDto.class));
    }
}
