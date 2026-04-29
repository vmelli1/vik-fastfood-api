package br.com.vikfastfood.api.orders.dto;

import java.util.UUID;

public record CategoriaResponse(
        UUID id,
        String nome
) {
}
