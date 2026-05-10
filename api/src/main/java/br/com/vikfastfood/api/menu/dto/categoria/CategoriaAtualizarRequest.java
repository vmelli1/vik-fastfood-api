package br.com.vikfastfood.api.menu.dto.categoria;

import java.util.UUID;

public record CategoriaAtualizarRequest(
        String nome,
        UUID estabelecimentoId
) {
}
