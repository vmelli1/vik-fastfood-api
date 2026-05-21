package br.com.vikfastfood.api.orders.service;

import br.com.vikfastfood.api.menu.model.CategoriaEstabelecimento;
import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import br.com.vikfastfood.api.menu.repository.CategoriaRepository;
import br.com.vikfastfood.api.menu.repository.ProdutoEstabelecimentoRepository;
import br.com.vikfastfood.api.orders.dto.CardapioResponse;
import br.com.vikfastfood.api.orders.dto.ProdutoCardapioResponse;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CardapioService {
    private final ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
    private final CategoriaRepository categoriaRepository;

    public CardapioService(ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository, CategoriaRepository categoriaRepository) {
        this.produtoEstabelecimentoRepository = produtoEstabelecimentoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CardapioResponse> cardapio(UUID estabelecimentoId) {
        List<CategoriaEstabelecimento> estabelecimentos = categoriaRepository.findAllByEstabelecimentoId(estabelecimentoId);
        return estabelecimentos.stream()
                .map(c -> new CardapioResponse(
                        c.getId(),
                        c.getNome(),
                        c.getProdutos().stream().filter(p -> p.isAtivo())
                                .map(p -> new ProdutoCardapioResponse(p.getId(),
                                        p.getNome(),
                                        p.getDescricao(),
                                        p.getPreco(),
                                        p.getUrlImagem())).toList()

                )).toList();
    }
}
