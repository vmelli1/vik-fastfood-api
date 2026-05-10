package br.com.vikfastfood.api.menu.dto.Produto;

import java.util.UUID;

public record DeletarProdutoRequest(
        UUID id,
        UUID estabelecimentoId
) {
}
