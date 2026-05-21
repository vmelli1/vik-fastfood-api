package br.com.vikfastfood.api.orders.service;


import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import br.com.vikfastfood.api.menu.repository.ProdutoEstabelecimentoRepository;
import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoRequest;
import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoResponse;
import br.com.vikfastfood.api.orders.model.ItemPedido;
import br.com.vikfastfood.api.orders.repository.ItemPedidoRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class ItemPedidoService {

    private final  ItemPedidoRepository itemPedidoRepository;
    private final ProdutoEstabelecimentoRepository  produtoRepository;


    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, ProdutoEstabelecimentoRepository produtoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ItemPedidoResponse pedido (ItemPedidoRequest dto){
        ProdutoEstabelecimento produto = produtoRepository.findById(dto.produtoId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido pedido = itemPedidoRepository.save(ItemPedido.builder()
                .produto(produto)
                .quantidade(dto.quantidade())
                .observacao(dto.observacao())
                .precoUnitario(produto.getPreco())
                .build());

        BigDecimal subTotal = pedido.getPrecoUnitario().multiply(new BigDecimal(dto.quantidade()));

        return new ItemPedidoResponse(
                produto.getNome(),
                pedido.getQuantidade(),
                pedido.getObservacao(),
                pedido.getPrecoUnitario(),
                subTotal
        );


    }
}

//principal meta:
//categoria vai ser utilizado o getter para o cliente visualizar
// //produto vai ser utilizado o getter para o cliente getVisualizar.
//itemPedido vamos utilizar o post para o cliente fazer o pedido, selecionar a quantidade e a observação
//pedido vai ser uma requisicao que ira ser encaminhado para o parte do admin com todas as informações do cliente e sobre o pedido;