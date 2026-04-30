package br.com.vikfastfood.api.users.repository;

import br.com.vikfastfood.api.users.model.UsuarioEstabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEstabelecimento, UUID> {
    boolean existsByEmail(String email);

    Optional<UsuarioEstabelecimento> findByEmail(String email);
}
