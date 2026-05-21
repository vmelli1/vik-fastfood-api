package br.com.vikfastfood.api.orders.repository;

import br.com.vikfastfood.api.orders.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PedidoRepository  extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByEstabelecimentoIdOrderByDataDesc(UUID estabelecimentoId);
}
