package br.com.fiap.nextia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.fiap.nextia.auth.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthorizationFilter authorizationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> 
            auth
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll() 
                .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("GERENTE_ESTOQUE", "ADMIN") 
                .requestMatchers(HttpMethod.POST, "/clients").hasAnyRole("GERENTE_CLIENTES", "ADMIN") 
                .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("GERENTE_ESTOQUE", "GERENTE_CLIENTES", "ADMIN") 
                .requestMatchers(HttpMethod.GET, "/clients/**").hasAnyRole("GERENTE_CLIENTES", "ADMIN")
                .anyRequest().denyAll()  
        );

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
