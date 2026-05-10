package br.com.vikfastfood.api.menu.dto.categoria;

import lombok.Builder;

import java.util.UUID;
@Builder
public record CategoriaResponse(
        UUID id,
        String nome
) {
}
