package com.example.test.config.security;

import com.example.test.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// Bearer token
// TODO: 01.02.2023 does not work
//        page http://127.0.0.1:9090/login/oauth2/code/taco-admin-client? not found
//        return http
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .oauth2Login(auth -> auth.loginPage("/oauth2/authorization/taco-admin-client"))
//                .oauth2Client(Customizer.withDefaults())
//                .build();

// Basic auth
        return http.authorizeHttpRequests()
                .requestMatchers(
                        "/design",
                        "/orders",
                        "/actuator/**"
                ).hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")
                .requestMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design", true)
                .and()
                .oauth2Login().loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

}
