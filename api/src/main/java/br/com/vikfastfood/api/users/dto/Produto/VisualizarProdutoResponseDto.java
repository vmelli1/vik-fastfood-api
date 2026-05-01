package br.com.vikfastfood.api.users.dto.Produto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;
@Builder
public record VisualizarProdutoResponseDto(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        BigDecimal custo,
        String urlImage
) {
}
