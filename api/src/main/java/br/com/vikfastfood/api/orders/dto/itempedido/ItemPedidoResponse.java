package br.com.vikfastfood.api.orders.dto.itempedido;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


public record ItemPedidoResponse(
        String nome,
        int quantidade,
        String observacao,
        BigDecimal precoUnitario,
        BigDecimal subTotal


) {


}
