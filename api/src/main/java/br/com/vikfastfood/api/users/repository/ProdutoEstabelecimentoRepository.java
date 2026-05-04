package br.com.vikfastfood.api.users.repository;

import br.com.vikfastfood.api.users.model.ProdutoEstabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoEstabelecimentoRepository extends JpaRepository<ProdutoEstabelecimento, UUID> {

    boolean existsByNomeAndEstabelecimentoId(String nome, UUID uuid);

    List<ProdutoEstabelecimento> findAllByCategoriaIdAndEstabelecimentoId(UUID categoriaId, UUID estabelecimentoId);
}
