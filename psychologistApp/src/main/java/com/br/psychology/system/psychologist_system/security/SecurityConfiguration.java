package com.br.psychology.system.psychologist_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration // Anotação para indicar que esta classe é uma configuração do Spring
@EnableWebSecurity // Anotação para habilitar a segurança web no aplicativo
public class SecurityConfiguration {

    // Método para criar um encoder de senhas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Método para configurar as regras de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configura as regras de autorização
        http.authorizeRequests((authorize)) ->
        authorize
                // Permite acesso público a recursos estáticos comuns
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // Requer que os usuários com a função "ADMIN" estejam autenticados para acessar a URL "/logout"
                .requestMatchers("/logout").hasRole("ADMIN")
                // Requer que os usuários com a função "ADMIN" estejam autenticados para acessar URLs começando com "/doutores/"
                .requestMatchers("/doutores/**").hasRole("ADMIN")
                // Requer que os usuários com a função "USER" estejam autenticados para acessar URLs começando com "/secretaria/"
                .requestMatchers("/secretaria/**").hasRole("USER")
                // Permite acesso público a todas as outras URLs
                .requestMatchers("/**").permitAll()
                // Requer autenticação para todas as outras solicitações não mapeadas explicitamente
                .anyRequest().authenticated()
                        )
        // Desativa a proteção CSRF
                                .csrf(AbstractHttpConfigurer::disable)
                // Configura a autenticação HTTP básica
                .httpBasic(withDefaults())
                // Configura o formulário de login
                .formLogin((form) -> form
                        // Define a página de login como "/login"
                        .loginPage("/login")
                        // Define a URL onde os dados do formulário de login serão submetidos
                        .loginProcessingUrl("/login")
                        // Define o manipulador de sucesso após o login
                        .successHandler((request, response, authentication) -> {
                            String redirectUrl = determineTargetUrl(authentication);
                            response.sendRedirect(request.getContextPath() + redirectUrl);
                        })
                        // Define a URL de falha após o login
                        .failureUrl("/login?error")
                )
                // Configura a funcionalidade de logout
                .logout((logout) -> logout
                        // Define a URL para redirecionar após o logout ser bem-sucedido
                        .logoutSuccessUrl("/login")
                        // Remove o cookie da sessão após o logout
                        .deleteCookies("JSESSIONID")
                        // Invalida a sessão do usuário após o logout
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    // Método para determinar a URL de redirecionamento com base no papel (role) do usuário
    private String determineTargetUrl(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "/psychologists/";
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return "/secretaries/";
        }

        throw new IllegalStateException("Usuário com autoridade desconhecida");
    }
}




