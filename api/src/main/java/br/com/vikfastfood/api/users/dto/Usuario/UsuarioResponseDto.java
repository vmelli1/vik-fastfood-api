package br.com.vikfastfood.api.users.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propriedades desconhecidas durante a desserialização JSON
public record UsuarioResponseDto(
        String email,
        boolean primeiroAcesso) {
}
