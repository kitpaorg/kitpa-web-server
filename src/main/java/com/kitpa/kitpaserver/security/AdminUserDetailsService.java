package com.kitpa.kitpaserver.security;

import com.kitpa.kitpaserver.entity.AdminAccount;
import com.kitpa.kitpaserver.repository.AdminAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {
    private final AdminAccountRepository adminAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        AdminAccount adminAccount = adminAccountRepository.findTop1By()
                .orElseThrow(() -> new UsernameNotFoundException("admin not found"));

        return new User(adminAccount.getUuid(),
                adminAccount.getUuid(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")));
    }
}
