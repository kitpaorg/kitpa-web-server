package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = "accountProblems")
    Optional<Account> findByUserId(String userId);

    Page<Account> findPageBy(PageRequest pageRequest);
    Page<Account> findPageByPhoneNumberContains(PageRequest pageRequest, @Param("phoneNumber") String phoneNumber);
}
