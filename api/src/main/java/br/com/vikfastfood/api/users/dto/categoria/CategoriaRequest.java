package br.com.vikfastfood.api.users.dto.categoria;

import java.util.UUID;

public record CategoriaRequest(
        String nome,
        UUID estabelecimentoId
) {
}
