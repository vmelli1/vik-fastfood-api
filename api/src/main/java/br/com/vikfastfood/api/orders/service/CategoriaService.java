package br.com.vikfastfood.api.orders.service;

import br.com.vikfastfood.api.orders.dto.CategoriaAtualizarRequest;
import br.com.vikfastfood.api.orders.dto.CategoriaRequest;
import br.com.vikfastfood.api.orders.dto.CategoriaResponse;
import br.com.vikfastfood.api.orders.model.Categoria;
import br.com.vikfastfood.api.orders.repository.CategoriaRepository;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CategoriaService {

    private final CategoriaRepository repository;


    private final EstabelecimentoRepository estabelecimentoRepository;

    public CategoriaService(CategoriaRepository repository, EstabelecimentoRepository estabelecimentoRepository) {
        this.repository = repository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }
    @Transactional
    public CategoriaResponse criarCategoia(CategoriaRequest dto) {
        if(repository.existsByNomeAndEstabelecimentoId(dto.nome(), dto.estabelecimentoId())){
            throw new IllegalArgumentException("Você já tem uma categoria chamada: " + dto.nome());
        }

        Estabelecimento est = estabelecimentoRepository.findById(dto.estabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());
        categoria.setEstabelecimento(est);

        Categoria salvar =  repository.save(categoria);

        return new CategoriaResponse(salvar.getId(), salvar.getNome());
    }

    @Transactional
    public CategoriaResponse atualizarCategoria(UUID id, CategoriaAtualizarRequest dto) {
        Categoria categoriaExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria  não encontrada! "));

        if(categoriaExistente.getNome().equalsIgnoreCase(dto.nome())){
            throw new IllegalArgumentException("Categoria com o mesmo nome, Insira outro nome para categoria: " + dto.nome());
        }

        if(repository.existsByNomeAndEstabelecimentoId(dto.nome(),categoriaExistente.getEstabelecimento().getId())) {
            throw new IllegalArgumentException("Você já possui outra categoria cadastrada com o nome: " + dto.nome());
        }

        categoriaExistente.setNome(dto.nome());

        // para nao esquecer mais ::: Instancia uma nova Categoria para receber o repositorio para atualizar
        Categoria categoriaAtualizado = repository.save(categoriaExistente);

        return new CategoriaResponse(categoriaAtualizado.getId(), categoriaAtualizado.getNome());

    }
}
