package com.kitpa.kitpaserver.security;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RoleUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Bean
    @Qualifier("userAuthenticationProvider")
    public AuthenticationProvider userAuthenticationProvider(@Autowired AccountRepository accountRepository,
                                                             @Qualifier("generalUserDetailsService") UserDetailsService userDetailsService,
                                                             @Autowired PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    @Qualifier("generalUserDetailsService")
    public UserDetailsService userDetailsService(@Autowired AccountRepository accountRepository) {
        return new RoleUserDetailsService(accountRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("logged in userId={}", userId);
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found by userId : " + userId));
        return new User(
                account.getUserId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
