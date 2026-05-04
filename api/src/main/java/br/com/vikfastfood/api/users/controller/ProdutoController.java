package br.com.vikfastfood.api.users.controller;

import br.com.vikfastfood.api.users.dto.Produto.CadastrarProdutoRequestDto;
import br.com.vikfastfood.api.users.dto.Produto.CadastrarProdutoResponseDto;
import br.com.vikfastfood.api.users.dto.Produto.VisualizarProdutoResponseDto;
import br.com.vikfastfood.api.users.service.ProdutoEstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoEstabelecimentoService service;

    @GetMapping
    public ResponseEntity<List<VisualizarProdutoResponseDto>> listar(@RequestParam  UUID categoriaId,  @RequestParam  UUID estabelecimentoId){
            List<VisualizarProdutoResponseDto> response = this.service.visualizarProduto(categoriaId, estabelecimentoId);
            return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> cadastraProduto(@Valid @RequestBody CadastrarProdutoRequestDto request){
        try{
            CadastrarProdutoResponseDto cadastrar = this.service.cadastrarProduto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cadastrar);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
