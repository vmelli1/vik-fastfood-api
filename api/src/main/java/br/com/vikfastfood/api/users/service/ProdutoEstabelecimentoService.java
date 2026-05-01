package br.com.vikfastfood.api.users.service;


import br.com.vikfastfood.api.users.dto.Produto.CadastrarProdutoRequestDto;
import br.com.vikfastfood.api.users.dto.Produto.CadastrarProdutoResponseDto;

import br.com.vikfastfood.api.users.dto.Produto.VisualizarProdutoResponseDto;
import br.com.vikfastfood.api.users.model.CategoriaEstabelecimento;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.model.ProdutoEstabelecimento;
import br.com.vikfastfood.api.users.repository.CategoriaRepository;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.repository.ProdutoEstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoEstabelecimentoService {
    @Autowired
    private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
    @Autowired
    private  EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public CadastrarProdutoResponseDto cadastrarProduto(CadastrarProdutoRequestDto dto){

         if(produtoEstabelecimentoRepository.existsByNomeAndEstabelecimentoId(dto.nome(), dto.estabelecimentoId())){
             throw new RuntimeException("Produto já existente neste estabelecimento");
         }

        CategoriaEstabelecimento categoriaEstabelecimento =  categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new  RuntimeException("Categoria não encontrada"));
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(dto.estabelecimentoId()).orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));

        ProdutoEstabelecimento cadastro = ProdutoEstabelecimento.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .custo(dto.custo())
                .urlImagem(dto.urlImage())
                .ativo(true)
                .categoria(categoriaEstabelecimento)
                .estabelecimento(estabelecimento)
                .build();
        categoriaEstabelecimento.getProdutos().add(cadastro);
        estabelecimento.getProdutos().add(cadastro);

        ProdutoEstabelecimento salvar = produtoEstabelecimentoRepository.save(cadastro);

        return CadastrarProdutoResponseDto.builder()
                .id(salvar.getId())
                .nome(salvar.getNome())
                .descricao(salvar.getDescricao())
                .preco(salvar.getPreco())
                .custo(salvar.getCusto())
                .urlImage(salvar.getUrlImagem())
                .ativo(salvar.isAtivo())
                .categoriaId(salvar.getCategoria().getId())
                .estabelecimentoId(estabelecimento.getId())
                .build();
    }

    @Transactional // Otimiza a leitura
    public List<VisualizarProdutoResponseDto> visualizarProduto(UUID categoriaId, UUID estabelecimentoId){
        List<ProdutoEstabelecimento> produto = produtoEstabelecimentoRepository.findAllByCategoriaIdAndEstabelecimentoId(categoriaId,estabelecimentoId);
        return produto.stream()
                .map(p -> VisualizarProdutoResponseDto.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .descricao(p.getDescricao())
                        .preco(p.getPreco())
                        .custo(p.getCusto())
                        .urlImage(p.getUrlImagem())
                        .build()
                )
                .toList();
    }
}
