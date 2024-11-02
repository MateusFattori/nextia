package br.com.fiap.nextia.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.nextia.model.Credentials;
import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.service.AuthService;

import java.io.IOException;
import java.util.List;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final AuthService authService;

    public AuthorizationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        // Verificar se o cabeçalho Authorization está presente e no formato Basic
        var header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrair o username e password do cabeçalho Authorization (formato Basic)
        try {
            String base64Credentials = header.replace("Basic ", "");
            String credentialsDecoded = new String(java.util.Base64.getDecoder().decode(base64Credentials));
            String[] credentialsArray = credentialsDecoded.split(":", 2);

            if (credentialsArray.length != 2) {
                throw new IllegalArgumentException("Credenciais inválidas");
            }

            String username = credentialsArray[0];
            String password = credentialsArray[1];

            // Criar o objeto Credentials e chamar o AuthService para autenticação
            var credentials = new Credentials(username, password);
            User user = authService.authenticate(credentials);

            if (user != null) {
                // Definir as autoridades com base na role do usuário autenticado, com o prefixo "ROLE_"
                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null, // Não estamos usando a senha aqui
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())) // Prefixo "ROLE_" antes do nome da role
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(403);
            response.addHeader("Content-Type", "application/json");
            response.getWriter().write("""
                {
                    "message": "%s"
                }
            """.formatted(e.getMessage()));
        }
    }
}
