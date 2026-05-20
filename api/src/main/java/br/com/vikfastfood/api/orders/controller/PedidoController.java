package br.com.vikfastfood.api.orders.controller;


import br.com.vikfastfood.api.orders.dto.pedido.PedidoRequest;
import br.com.vikfastfood.api.orders.dto.pedido.PedidoResponse;

import br.com.vikfastfood.api.orders.model.EnumStatus;
import br.com.vikfastfood.api.orders.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido (@Valid   @RequestBody PedidoRequest dto) {
        PedidoResponse response = this.pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar(@RequestParam UUID estabelecimentoId) {
        List<PedidoResponse> response = pedidoService.listarPorEstabelecimento(estabelecimentoId);
        return ResponseEntity.ok(response);
    }

    //ver depois tbm
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        try {
            String statusTexto = body.get("status");
            // Converte a String para o Enum correspondente
            EnumStatus novoStatus = EnumStatus.valueOf(statusTexto.toUpperCase().trim());

            pedidoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | NullPointerException e) {
            // Se o valor for inválido ou nulo, retorna 400 em vez de 500
            return ResponseEntity.badRequest().body("Status inválido ou não informado.");
        }
    }
}
