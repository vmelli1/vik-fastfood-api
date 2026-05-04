package br.com.vikfastfood.api.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "produtos")
public class ProdutoEstabelecimento {
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
    private CategoriaEstabelecimento categoria;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;


}
