package br.com.fiap.nextia.dto;

import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.model.Role;

public record UserRequest(
        String username,
        String password,
        Role role
) {
    public User toModel() {
        return new User(
                null,
                username,
                password,
                role
        );
    }
}
