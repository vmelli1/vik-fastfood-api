package br.com.vikfastfood.api.orders.dto.pedido;

import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoRequest;
import br.com.vikfastfood.api.orders.model.EnumPagamento;

import java.util.List;
import java.util.UUID;

public record PedidoRequest(
        String clienteNome,
        String clienteTelefone,
        String clienteEndereco,
        EnumPagamento tipoPagamento,
        UUID estabelecimentoId,
        List<ItemPedidoRequest> itens
) {
}
