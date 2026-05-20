package br.com.vikfastfood.api.orders.dto;

import java.util.List;
import java.util.UUID;

public record CardapioResponse(
        UUID id,
        String nomeCategoria,
        List<ProdutoCardapioResponse> produtos
) {
}
