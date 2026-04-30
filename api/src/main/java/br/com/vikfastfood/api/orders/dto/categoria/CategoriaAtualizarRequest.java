package br.com.vikfastfood.api.orders.dto.categoria;

import java.util.UUID;

public record CategoriaAtualizarRequest(
        String nome,
        UUID estabelecimentoId
) {
}
