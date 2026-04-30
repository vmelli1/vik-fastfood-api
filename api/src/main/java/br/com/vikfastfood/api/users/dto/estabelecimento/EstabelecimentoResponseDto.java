package br.com.vikfastfood.api.users.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.UUID;
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record EstabelecimentoResponseDto(
        UUID id,
        String nome,
        String cnpj,
        String whatsapp,
        String endereco
) {
}
