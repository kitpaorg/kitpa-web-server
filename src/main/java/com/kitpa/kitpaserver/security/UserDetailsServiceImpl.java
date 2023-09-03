package com.kitpa.kitpaserver.security;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found by userId : " + userId));
        return new User(
                account.getUserId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
