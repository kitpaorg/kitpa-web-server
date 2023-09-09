package com.kitpa.kitpaserver.repository;

import com.kitpa.kitpaserver.entity.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
    Optional<AdminAccount> findTop1By();
}
