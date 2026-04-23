package br.com.vikfastfood.api.users.dto.Usuario;

import br.com.vikfastfood.api.users.model.Estabelecimento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioRequestDto(
    String email,
    String senha,
    UUID estabelecimentoId
) {
}
