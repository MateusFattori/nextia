package br.com.fiap.nextia.dto;

import br.com.fiap.nextia.model.User;

public record UserProfileResponse(
        String username,
        String role
) {
    public UserProfileResponse(User user) {
        this(user.getUsername(), user.getRole().toString()); 
    }
}
