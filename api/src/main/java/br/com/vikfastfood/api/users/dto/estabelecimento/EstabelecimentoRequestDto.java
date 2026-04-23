package br.com.vikfastfood.api.users.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public record EstabelecimentoRequestDto(
        String nome,
        String cnpj,
        String whatsapp,
        String endereco,
        BigDecimal taxaEntrega
) {
}
