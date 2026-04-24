package br.com.vikfastfood.api.orders.dto;

import java.util.UUID;

public record CategoriaAtualizarRequest(
        String nome,
        UUID estabelecimentoId
) {
}
