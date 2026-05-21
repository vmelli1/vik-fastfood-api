package br.com.vikfastfood.api.orders.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoCardapioResponse(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String urlImagem
) {
}
