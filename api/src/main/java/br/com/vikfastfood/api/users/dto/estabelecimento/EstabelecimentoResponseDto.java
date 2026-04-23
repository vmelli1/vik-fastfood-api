package br.com.vikfastfood.api.users.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EstabelecimentoResponseDto(
        UUID id,
        String nome,
        String cnpj,
        String whatsapp,
        String endereco
) {
}
