package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountExam;
import com.kitpa.kitpaserver.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountExamRepository extends JpaRepository<AccountExam, Long>{
    Boolean existsByAccountAndExam(Account account, Exam exam);

    Optional<AccountExam> findByAccountAndExam(Account account, Exam exam);

}
