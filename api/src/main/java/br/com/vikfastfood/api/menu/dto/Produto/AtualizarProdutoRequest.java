package br.com.vikfastfood.api.menu.dto.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarProdutoRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        BigDecimal custo,
        String urlImagem,
        UUID estabelecimentoId,
        UUID categoriaId
) {
}
