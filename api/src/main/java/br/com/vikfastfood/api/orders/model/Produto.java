package br.com.vikfastfood.api.orders.model;

import br.com.vikfastfood.api.users.model.Estabelecimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (columnDefinition = "BINARY(16)")
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private BigDecimal custo;
    private String urlImagem;

    private boolean ativo = true;// Por padrão, o produto é criado como ativo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;




}
