package br.com.vikfastfood.api.menu.dto.Produto;

import java.util.UUID;

public record DeletarProdutoResponse(
        UUID id,
        String nome
) {
}
