package br.com.vikfastfood.api.orders.model;

import br.com.vikfastfood.api.menu.model.ProdutoEstabelecimento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)") // oque isso faz?
    private UUID id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstabelecimento produto;

    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    private BigDecimal precoUnitario;
    private String observacao;
}
