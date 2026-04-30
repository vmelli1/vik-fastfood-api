package br.com.vikfastfood.api.users.repository;

import br.com.vikfastfood.api.orders.model.Categoria;
import br.com.vikfastfood.api.users.model.CategoriaEstabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEstabelecimento, UUID> {
    boolean existsByNome(String nome);

    boolean existsByNomeAndEstabelecimentoId(String nome, UUID estabelecimentoId);


    List<CategoriaEstabelecimento> id(UUID id);

    List<CategoriaEstabelecimento> findAllByEstabelecimentoId(UUID estabelecimentoId);
}
