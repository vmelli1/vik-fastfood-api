package br.com.vikfastfood.api.users.service;

import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoService {
    @Autowired
    private EstabelecimentoRepository repository;
    @Transactional
    public EstabelecimentoResponseDto cadastrar(EstabelecimentoRequestDto dto){
        if(repository.existsByCnpj(dto.cnpj())){
            throw new RuntimeException("Já existe um estabelecimento com este CNPJ.");
        }

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(dto.nome());
        estabelecimento.setCnpj(dto.cnpj());
        estabelecimento.setWhatsapp(dto.whatsapp());
        estabelecimento.setEndereco(dto.endereco());
        estabelecimento.setTaxaEntrega(dto.taxaEntrega());
        Estabelecimento salvar = repository.save(estabelecimento);

        return new EstabelecimentoResponseDto(
                salvar.getNome(),
                salvar.getCnpj(),
                salvar.getWhatsapp(),
                salvar.getEndereco()
        );
    }
}
