package br.com.vikfastfood.api.menu.service;

import br.com.vikfastfood.api.menu.dto.Produto.*;

import br.com.vikfastfood.api.menu.model.CategoriaEstabelecimento;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import br.com.vikfastfood.api.menu.repository.CategoriaRepository;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.menu.repository.ProdutoEstabelecimentoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProdutoEstabelecimentoService {
    @Autowired
    private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public CadastrarProdutoResponseDto cadastrarProduto(CadastrarProdutoRequestDto dto) {

        if (produtoEstabelecimentoRepository.existsByNomeAndEstabelecimentoId(dto.nome(), dto.estabelecimentoId())) {
            throw new RuntimeException("Produto já existente neste estabelecimento");
        }

        CategoriaEstabelecimento categoriaEstabelecimento = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(dto.estabelecimentoId()).orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));

        ProdutoEstabelecimento novoProduto = ProdutoEstabelecimento.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .custo(dto.custo())
                .urlImagem(dto.urlImage())
                .ativo(true)
                .categoria(categoriaEstabelecimento)
                .estabelecimento(estabelecimento)
                .build();
        categoriaEstabelecimento.getProdutos().add(novoProduto);
        estabelecimento.getProdutos().add(novoProduto);

        ProdutoEstabelecimento produtoSalvo = produtoEstabelecimentoRepository.save(novoProduto);

        return CadastrarProdutoResponseDto.builder()
                .id(produtoSalvo.getId())
                .nome(produtoSalvo.getNome())
                .descricao(produtoSalvo.getDescricao())
                .preco(produtoSalvo.getPreco())
                .custo(produtoSalvo.getCusto())
                .urlImage(produtoSalvo.getUrlImagem())
                .ativo(produtoSalvo.isAtivo())
                .categoriaId(produtoSalvo.getCategoria().getId())
                .estabelecimentoId(estabelecimento.getId())
                .build();
    }

    @Transactional(readOnly = true) // Otimiza a leitura
    public List<VisualizarProdutoResponseDto> visualizarProduto(UUID categoriaId, UUID estabelecimentoId) {
        List<ProdutoEstabelecimento> produto = produtoEstabelecimentoRepository.findAllByCategoriaIdAndEstabelecimentoId(categoriaId, estabelecimentoId);
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

    @Transactional
    public void excluirProduto(DeletarProdutoRequest deletarProdutoRequest) {
        ProdutoEstabelecimento produtoEstabelecimento = produtoEstabelecimentoRepository.findById(deletarProdutoRequest.id())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produtoEstabelecimento.getEstabelecimento().getId().equals(deletarProdutoRequest.estabelecimentoId())) {
            log.warn("Tentativa de invasão! Estabelecimento {} tentou deletar produto de outro dono.", deletarProdutoRequest.estabelecimentoId());
            throw new RuntimeException("Você não tem permissão para deletar este produto.");
        }

        produtoEstabelecimentoRepository.delete(produtoEstabelecimento);

    }

    @Transactional
    public void produtosAtivos(ProdutoIsAtivoRequest dto) {
        ProdutoEstabelecimento produtoIsAtivo = produtoEstabelecimentoRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado para poder ativar."));

        if (!produtoIsAtivo.getEstabelecimento().getId().equals(dto.estabelecimentoId())) {
            log.warn("AVISO: TENTATIVA DE INVASÃO! ESTABELECIMENTO {} TENTOU ATIVAR OU DESATIVAR PRODUTO DE OUTRO DONO.", dto.estabelecimentoId());
            throw new RuntimeException("Voçê não tem permissao para alterar o ativo do produto.");
        }

        produtoIsAtivo.setAtivo(dto.isAtivo());
        log.info("Status do produto '{}' atualizado para: {}", produtoIsAtivo.getNome(), dto.isAtivo());
    }

    @Transactional
    public void atualizarProduto(AtualizarProdutoRequest dto, UUID id) {
        ProdutoEstabelecimento produtoAtualizar = produtoEstabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!produtoAtualizar.getEstabelecimento().getId().equals(dto.estabelecimentoId())) {
            log.warn("AVISO: TENTATIVA DE INVASÃO! ESTABELECIMENTO {} TENTOU ATUALIZAR O PRODUTO DE OUTRO DONO.", dto.estabelecimentoId());
            throw new RuntimeException("Acesso negado.");
        }

        if (!produtoAtualizar.getNome().equalsIgnoreCase(dto.nome()) && produtoEstabelecimentoRepository.existsByNomeAndEstabelecimentoId(dto.nome(), dto.estabelecimentoId())) {
            throw new RuntimeException("Produto com o mesmo nome, tente outro nome.");

        }

        produtoAtualizar.atualizar(dto);

//        produtoAtualizar.setNome(dto.nome());
//        produtoAtualizar.setDescricao(dto.descricao());
//        produtoAtualizar.setPreco(dto.preco());
//        produtoAtualizar.setCusto(dto.custo());
//        produtoAtualizar.setUrlImagem(dto.urlImagem());

        log.info("Produto ID: {} atualizado com sucesso.", id);
    }


}
