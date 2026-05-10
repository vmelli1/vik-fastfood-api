package br.com.vikfastfood.api.menu.model;


import br.com.vikfastfood.api.users.model.Estabelecimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"produtos", "estabelecimento"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "categorias")
public class CategoriaEstabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoEstabelecimento> produtos= new ArrayList<>();
}
