package br.com.vikfastfood.api.users.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioRequestDto(
    String email,
    String senha
) {
}
