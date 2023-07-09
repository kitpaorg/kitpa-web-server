package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountExamRepositorySupport {
    Page<AccountExam> findByCanCancellation(Pageable pageable, Account account);
}
