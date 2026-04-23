package br.com.vikfastfood.api.users.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EstabelecimentoResponseDto(
        String nome,
        String cnpj,
        String whatsapp,
        String endereco
) {
}
