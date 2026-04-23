package br.com.vikfastfood.api.orders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)") // oque isso faz?
    private UUID id;

    @ManyToOne(fetch =  FetchType.LAZY)  @JoinColumn(name = "pedido_id")private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY)  @JoinColumn(name = "produto_id") private Produto produto;
    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    private BigDecimal precoUnitario;
    private String observacao;
}
