package br.com.vikfastfood.api.orders.repository;

import br.com.vikfastfood.api.orders.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    boolean existsByNome(String nome);

    boolean existsByNomeAndEstabelecimentoId(String nome, UUID estabelecimentoId);




}
