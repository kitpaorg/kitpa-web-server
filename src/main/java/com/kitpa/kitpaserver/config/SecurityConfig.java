package com.kitpa.kitpaserver.config;

import com.kitpa.kitpaserver.repository.AccountRepository;
import com.kitpa.kitpaserver.repository.AdminAccountRepository;
import com.kitpa.kitpaserver.security.AdminUserDetailsService;
import com.kitpa.kitpaserver.security.RoleUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Order(2)
public class SecurityConfig {
    @Bean(name = "userAuthenticationProvider")
    public AuthenticationProvider userAuthenticationProvider(@Qualifier("generalUserDetailsService") UserDetailsService userDetailsService,
                                                             @Autowired PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean(name = "generalUserDetailsService")
    public UserDetailsService userDetailsService(@Autowired AccountRepository accountRepository) {
        return new RoleUserDetailsService(accountRepository);
    }

    @Bean(name = "adminAuthenticationProvider")
    public AuthenticationProvider adminAuthenticationProvider(@Qualifier("adminUserDetailsService") UserDetailsService adminDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        return provider;
    }

    @Bean(name = "adminUserDetailsService")
    public UserDetailsService userDetailsService(@Autowired AdminAccountRepository adminAccountRepository) {
        return new AdminUserDetailsService(adminAccountRepository);
    }

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http,
                                               @Qualifier("userAuthenticationProvider") AuthenticationProvider userAuthenticationProvider,
                                               @Qualifier("adminAuthenticationProvider") AuthenticationProvider adminAuthenticationProvider) throws Exception {
        http
                .antMatcher("/admin/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeRequests()

                .antMatchers("/admin/accounts/login")
                .permitAll()

                .antMatchers("/admin/**")
                .hasRole("ADMIN")

                .and()
                .formLogin(form ->
                        form
                                .loginPage("/admin/accounts/login")
                                .loginProcessingUrl("/admin/accounts/login")
                                .defaultSuccessUrl("/admin", true)
                                .failureUrl("/admin/accounts/login?error=true")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutUrl("/admin/accounts/logout")
                                .logoutSuccessUrl("/admin/accounts/login")
                                .deleteCookies("JSESSIONID"))

                .antMatcher("/**")
                .authenticationProvider(userAuthenticationProvider)
                .authorizeRequests()

                .antMatchers("/", "/accounts/login")
                .permitAll()

                .antMatchers("/**")
                .hasRole("USER")

                .and()
                .formLogin(form ->
                        form
                                .loginPage("/accounts/login")
                                .loginProcessingUrl("/accounts/login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/accounts/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/accounts/logout")
                                .logoutSuccessUrl("/")
                                .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}
