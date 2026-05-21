package br.com.vikfastfood.api.orders.repository;

import br.com.vikfastfood.api.orders.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

}
