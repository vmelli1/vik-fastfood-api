package br.com.vikfastfood.api.users.repository;

import br.com.vikfastfood.api.users.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, UUID> {
    boolean existsByCnpj(String cnpj);
}
