package br.com.vikfastfood.api.orders.dto.categoria;

import java.util.UUID;

public record CategoriaRequest(
        String nome,
        UUID estabelecimentoId
) {
}
