package com.kitpa.kitpaserver.config;

import com.kitpa.kitpaserver.repository.AdminAccountRepository;
import com.kitpa.kitpaserver.security.AdminUserDetailsService;
import com.kitpa.kitpaserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Order(1)
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    @Bean(name="adminAuthenticationProvider")
    public AuthenticationProvider adminAuthenticationProvider(@Qualifier("adminUserDetailsService") UserDetailsService adminDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        return provider;
    }

    @Bean(name="adminUserDetailsService")
    public UserDetailsService userDetailsService(@Autowired AdminAccountRepository adminAccountRepository) {
        return new AdminUserDetailsService(adminAccountRepository);
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http,
                                           @Qualifier("adminAuthenticationProvider") AuthenticationProvider adminAuthenticationProvider) throws Exception {
        http
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeRequests()

                .antMatchers("/admin/login/**")
                .permitAll()

                .antMatchers("/h2-console/**")
                .permitAll()

                .antMatchers("/admin/**")
                .hasRole("ADMIN")

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()

                .and()
                .formLogin()
                .loginPage("/admin/accounts/login")
                .loginProcessingUrl("/admin/accounts/login")
                .defaultSuccessUrl("/admin", true)
                .failureUrl("/login?error=true")

                .and()
                .logout()
                .deleteCookies("JSESSIONID");
        return http.build();
    }
}
