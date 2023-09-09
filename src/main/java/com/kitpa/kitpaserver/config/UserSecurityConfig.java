package com.kitpa.kitpaserver.config;

import com.kitpa.kitpaserver.repository.AccountRepository;
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
@Order(2)
public class UserSecurityConfig {
    @Bean(name="userAuthenticationProvider")
    public AuthenticationProvider userAuthenticationProvider(@Qualifier("generalUserDetailsService") UserDetailsService userDetailsService,
                                                              @Autowired PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean(name="generalUserDetailsService")
    public UserDetailsService userDetailsService(@Autowired AccountRepository accountRepository) {
        return new RoleUserDetailsService(accountRepository);
    }

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http,
                                               @Qualifier("userAuthenticationProvider") AuthenticationProvider userAuthenticationProvider) throws Exception {
        http
                .authenticationProvider(userAuthenticationProvider)
                .authorizeRequests()

                .antMatchers("/","/accounts/login","/accounts/login/**")
                .permitAll()

                .antMatchers("/h2-console/**")
                .permitAll()

                .antMatchers("/**")
                .hasRole("USER")

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()

                .and()
                .formLogin()
                .loginPage("/accounts/login")
                .loginProcessingUrl("/accounts/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")

                .and()
                .logout()
                .logoutUrl("/accounts/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID");
        return http.build();
    }

}
