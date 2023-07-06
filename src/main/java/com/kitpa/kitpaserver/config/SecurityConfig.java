package com.kitpa.kitpaserver.config;

import com.kitpa.kitpaserver.security.UserDetailsServiceImpl;
import com.kitpa.kitpaserver.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")

                .antMatchers("/anonymous*")
                .anonymous()

                .antMatchers("/h2-console/**")
                .permitAll()

                .antMatchers("/account/login/**", "/account/register")
                .permitAll()

                .anyRequest()
                .authenticated()

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()

                .and()
                .formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/main", true)
                .failureUrl("/login?error=true")

                .and()
                .logout()
                .deleteCookies("JSESSIONID");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired AccountRepository accountRepository) {
        return new UserDetailsServiceImpl(accountRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
