package br.com.vikfastfood.api.menu.dto.Produto;

import java.util.UUID;

public record ProdutoIsAtivoRequest(
        UUID id,
        UUID estabelecimentoId,
        boolean isAtivo
) {
}
