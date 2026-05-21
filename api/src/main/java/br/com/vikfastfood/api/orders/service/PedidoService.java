package br.com.vikfastfood.api.orders.service;

import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import br.com.vikfastfood.api.menu.repository.ProdutoEstabelecimentoRepository;
import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoRequest;
import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoResponse;
import br.com.vikfastfood.api.orders.dto.pedido.PedidoRequest;
import br.com.vikfastfood.api.orders.dto.pedido.PedidoResponse;
import br.com.vikfastfood.api.orders.model.EnumStatus;
import br.com.vikfastfood.api.orders.model.ItemPedido;
import br.com.vikfastfood.api.orders.model.Pedido;
import br.com.vikfastfood.api.orders.repository.PedidoRepository;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.service.EstabelecimentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;

    public PedidoService(EstabelecimentoRepository estabelecimentoRepository, PedidoRepository pedidoRepository, ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoEstabelecimentoRepository = produtoEstabelecimentoRepository;
    }


    @Transactional
    public PedidoResponse criarPedido(PedidoRequest pedidoRequest) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(pedidoRequest.estabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));

        Pedido pedido = new Pedido();
        pedido.setStatus(EnumStatus.PENDENTE);
        pedido.setData(LocalDateTime.now());
        pedido.setClienteNome(pedidoRequest.clienteNome());
        pedido.setClienteTelefone(pedidoRequest.clienteTelefone());
        pedido.setClienteEndereco(pedidoRequest.clienteEndereco());
        pedido.setTipoPagamento(pedidoRequest.tipoPagamento());
        pedido.setEstabelecimento(estabelecimento);

        for (ItemPedidoRequest itemPedidoRequest : pedidoRequest.itens()) {
            ProdutoEstabelecimento produto = produtoEstabelecimentoRepository.findById(itemPedidoRequest.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemPedidoRequest.quantidade());
            itemPedido.setObservacao(itemPedidoRequest.observacao());
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }

         BigDecimal total = pedido.getItens().stream()
                 .map(p -> p.getPrecoUnitario().multiply(new BigDecimal(p.getQuantidade())))
                 .reduce(BigDecimal.ZERO, BigDecimal::add);

            pedido.setTotal(total);
            pedidoRepository.save(pedido);

            return new PedidoResponse(
                    pedido.getId(),
                    pedido.getData(),
                    pedido.getStatus(),
                    pedido.getTipoPagamento(),
                    pedido.getTotal(),

                    // remover depois
                    pedido.getClienteNome(),
                    pedido.getClienteTelefone(),
                    pedido.getClienteEndereco(),
                    pedido.getItens().stream()     // novo
                            .map(i -> new ItemPedidoResponse(
                                    i.getProduto().getNome(),
                                    i.getQuantidade(),
                                    i.getObservacao(),
                                    i.getPrecoUnitario(),
                                    i.getPrecoUnitario().multiply(new BigDecimal(i.getQuantidade()))
                            ))
                            .toList()
            );

    }

    public List<PedidoResponse> listarPorEstabelecimento(UUID estabelecimentoId) {
        return pedidoRepository
                .findByEstabelecimentoIdOrderByDataDesc(estabelecimentoId)
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getData(),
                        p.getStatus(),
                        p.getTipoPagamento(),
                        p.getTotal(),

                        // apagar depois de tirar duvida
                        p.getClienteNome(),
                        p.getClienteTelefone(),
                        p.getClienteEndereco(),
                        p.getItens().stream().map(i -> new ItemPedidoResponse(
                                i.getProduto().getNome(),
                                i.getQuantidade(),
                                i.getObservacao(),
                                i.getPrecoUnitario(),
                                i.getPrecoUnitario().multiply(new BigDecimal(i.getQuantidade()))

                        )).toList()
                ))
                .toList();

    }

    // verificar depois
    public void atualizarStatus(UUID id, EnumStatus novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);
    }
}
