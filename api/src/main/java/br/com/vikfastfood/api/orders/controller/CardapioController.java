package br.com.vikfastfood.api.orders.controller;


import br.com.vikfastfood.api.orders.dto.CardapioResponse;
import br.com.vikfastfood.api.orders.service.CardapioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapios")
@CrossOrigin(origins = "*")
public class CardapioController {
    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping("/{estabelecimentoId}")
    public ResponseEntity<List<CardapioResponse>> cardapio(@PathVariable UUID estabelecimentoId){
        List<CardapioResponse> response = cardapioService.cardapio(estabelecimentoId);
        return ResponseEntity.ok(response);
    }
}
