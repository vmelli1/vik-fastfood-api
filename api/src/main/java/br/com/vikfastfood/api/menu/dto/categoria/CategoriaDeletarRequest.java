package br.com.vikfastfood.api.menu.dto.categoria;

import java.util.UUID;

public record CategoriaDeletarRequest(
        UUID id,
        UUID estabelecimentoId
) {
}
