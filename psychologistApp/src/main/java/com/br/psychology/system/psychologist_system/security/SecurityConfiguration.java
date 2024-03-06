package com.br.psychology.system.psychologist_system.security;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Anotação para indicar que esta classe é uma configuração do Spring
@EnableWebSecurity // Anotação para habilitar a segurança web no aplicativo
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authorize) ->
                        authorize
                                .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll()
                                .requestMatchers("/logout").hasRole("ADMIN")
                                .requestMatchers("/psychologists/**").hasRole("ADMIN")
                                .requestMatchers("/secretary/**").hasRole("USER")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String redirectUrl = determineTargetUrl(authentication);
                            response.sendRedirect(request.getContextPath() + redirectUrl);
                        })
                        .failureUrl("/login?error")
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    private String determineTargetUrl(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "/psychologists/";
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "/secretary/";
        }

        throw new IllegalStateException("Usuário com autoridade desconhecida");
    }
}