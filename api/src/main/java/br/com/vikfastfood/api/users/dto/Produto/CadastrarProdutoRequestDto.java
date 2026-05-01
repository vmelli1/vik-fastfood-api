package br.com.vikfastfood.api.users.dto.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record CadastrarProdutoRequestDto(
        String nome,
        String descricao,
        BigDecimal preco,
        BigDecimal custo,
        String urlImage,
        Boolean ativo,
        UUID estabelecimentoId,
        UUID categoriaId
) {
}
