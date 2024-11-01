package br.com.fiap.nextia.dto;

import br.com.fiap.nextia.model.User;

public record UserResponse(
        Long id,
        String username,
        String role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }
}
