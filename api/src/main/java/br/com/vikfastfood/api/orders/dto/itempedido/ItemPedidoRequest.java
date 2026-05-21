package br.com.vikfastfood.api.orders.dto.itempedido;

import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ItemPedidoRequest(
        UUID produtoId,
        @Positive int quantidade,
        @Size(max = 126) String observacao

) {
}
