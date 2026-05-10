package br.com.vikfastfood.api.menu.dto.categoria;

import java.util.UUID;

public record CategoriaRequest(
        String nome,
        UUID estabelecimentoId
) {
}
