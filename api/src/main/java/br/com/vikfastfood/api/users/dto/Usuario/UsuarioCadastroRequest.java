package br.com.vikfastfood.api.users.dto.Usuario;

import java.util.UUID;

public record UsuarioCadastroRequest(
        String email,
        String senha,
        UUID estabelecimentoId
) {
}
