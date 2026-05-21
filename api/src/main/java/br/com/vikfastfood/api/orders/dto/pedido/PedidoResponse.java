package br.com.vikfastfood.api.orders.dto.pedido;

import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoResponse;
import br.com.vikfastfood.api.orders.model.EnumPagamento;
import br.com.vikfastfood.api.orders.model.EnumStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponse(
        UUID id,
        LocalDateTime data,
        EnumStatus status,
        EnumPagamento tipoPagamento,
        BigDecimal total,

        // remover depois depender
        String clienteNome,
        String clienteTelefone,
        String clienteEndereco,
        List<ItemPedidoResponse> itens
) {
}
