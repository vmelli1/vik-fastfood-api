package br.com.vikfastfood.api.orders.controller;

import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoRequest;
import br.com.vikfastfood.api.orders.dto.itempedido.ItemPedidoResponse;
import br.com.vikfastfood.api.orders.model.ItemPedido;
import br.com.vikfastfood.api.orders.service.ItemPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
@CrossOrigin(origins = "*")
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponse> cadastrar(@Valid @RequestBody ItemPedidoRequest itemPedido){
        ItemPedidoResponse response = this.itemPedidoService.pedido(itemPedido);
        return ResponseEntity.ok(response);
    }
}
