package br.com.vikfastfood.api.users.service;

import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class EstabelecimentoService {
    @Autowired
    private EstabelecimentoRepository repository;
    @Transactional
    public EstabelecimentoResponseDto cadastrar(EstabelecimentoRequestDto dto){

        String cnpjLimpo = dto.cnpj().replaceAll("\\D", "");

        if(repository.existsByCnpj(cnpjLimpo)){
            log.error("Usuario esta tentando cadastrar com um CNPJ EXISTENTE: {}", dto.cnpj());
            throw new RuntimeException("Já existe um estabelecimento com este CNPJ.");
        }


        Estabelecimento  estabelecimento  = Estabelecimento.builder()
                .nome(dto.nome())
                .cnpj(dto.cnpj())
                .whatsapp(dto.whatsapp())
                .endereco(dto.endereco())
                .taxaEntrega(dto.taxaEntrega())
                .build();

        Estabelecimento salvar = repository.save(estabelecimento);

        return  EstabelecimentoResponseDto.builder()
                .id(salvar.getId())
                .nome(salvar.getNome())
                .cnpj(salvar.getCnpj())
                .whatsapp(salvar.getWhatsapp())
                .endereco(salvar.getEndereco())
                .build();
    }
}
