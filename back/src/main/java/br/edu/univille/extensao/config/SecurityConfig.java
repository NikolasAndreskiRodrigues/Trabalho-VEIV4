package br.edu.univille.extensao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/login",
                    "/register",
                    "/css/**",
                    "/js/**"
                ).permitAll()
                .anyRequest().authenticated()
            ).formLogin(form -> form
                .loginPage("/loginusuario")
                .defaultSuccessUrl("/paginainicial", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/loginusuario?logout")
                .permitAll()
            );
        return http.build();
    }
}