package br.com.vikfastfood.api.users.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioRequestNovaSenhaDto(
        String email,
        String senhaAtual,
        String novaSenha
) {
}
