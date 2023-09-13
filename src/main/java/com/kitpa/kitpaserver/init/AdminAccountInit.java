package com.kitpa.kitpaserver.init;

import com.kitpa.kitpaserver.entity.AdminAccount;
import com.kitpa.kitpaserver.repository.AdminAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class AdminAccountInit {
    private final AdminAccountRepository adminAccountRepository;

    @Value("${admin.password}")
    private String uuid;

    @PostConstruct
    public void initializeAdminAccount() {
        if (adminAccountRepository.findTop1By().isPresent()) {
            return;
        }
        AdminAccount adminAccount = new AdminAccount(uuid);
        adminAccountRepository.saveAndFlush(adminAccount);
    }
}
