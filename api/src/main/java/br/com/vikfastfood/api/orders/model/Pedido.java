package br.com.vikfastfood.api.orders.model;

import br.com.vikfastfood.api.users.model.Estabelecimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)") // Usando UUID como tipo de dado para o ID
    private UUID id;
    private LocalDateTime data;
    @Column(nullable = false)
    private String clienteNome;
    @Column(nullable = false)
    private String clienteTelefone;
    @Column(nullable = false)
    private String clienteEndereco;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumPagamento tipoPagamento;
    @Column(nullable = false)
    private BigDecimal total;
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

}
