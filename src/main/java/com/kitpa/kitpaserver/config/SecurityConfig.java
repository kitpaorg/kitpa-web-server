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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
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
    @Order(0)
    public SecurityFilterChain adminFilterChain(HttpSecurity http,
                                                @Qualifier("adminAuthenticationProvider") AuthenticationProvider adminAuthenticationProvider) throws Exception {

        http
                .authenticationProvider(adminAuthenticationProvider)
                .antMatcher("/admin/**")
                .authorizeHttpRequests(re->
                    re.anyRequest().hasRole("ADMIN")
                )
                .formLogin(form ->
                        form
                                .loginPage("/admin/accounts/login")
                                .loginProcessingUrl("/admin/accounts/admin_login")
                                .defaultSuccessUrl("/admin", true)
                                .failureUrl("/admin/accounts/login?error=true")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutUrl("/admin/accounts/logout")
                                .logoutSuccessUrl("/admin/accounts/login")
                                .deleteCookies("JSESSIONID"));
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain userFilterChain(HttpSecurity http,
                                               @Qualifier("userAuthenticationProvider") AuthenticationProvider userAuthenticationProvider) throws Exception {
        http
                .authenticationProvider(userAuthenticationProvider)
                .antMatcher("/**")
                .authorizeHttpRequests(re ->
                        re.anyRequest().hasRole("USER")
                )
                .formLogin(form ->
                        form
                                .loginPage("/accounts/login")
                                .loginProcessingUrl("/accounts/user_login")
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

//                .and()
//
//                .authorizeRequests()
//                .antMatchers("/admin/**")
//                .hasRole("ADMIN")
//
//                .and()
//
//                .authorizeRequests()
//                .antMatchers("/**")
//                .hasRole("USER");
        return http.build();
    }

}
