package br.com.vikfastfood.api.users.controller;


import br.com.vikfastfood.api.users.dto.categoria.CategoriaAtualizarRequest;
import br.com.vikfastfood.api.users.dto.categoria.CategoriaDeletarRequest;
import br.com.vikfastfood.api.users.dto.categoria.CategoriaRequest;
import br.com.vikfastfood.api.users.dto.categoria.CategoriaResponse;
import br.com.vikfastfood.api.users.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaEstalecimentoController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> ListarCategorias(@RequestParam UUID estabelecimentoId) {
        try {
            List<CategoriaResponse> response =  this.service.listarCategorias(estabelecimentoId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping()
    public ResponseEntity<CategoriaResponse> cadastrarCategoria(@RequestBody CategoriaRequest categoriaRequest){
        try{
        CategoriaResponse cadastroResposta = this.service.criarCategoia(categoriaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroResposta);}
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{estabelecimentoId}")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(@RequestBody CategoriaAtualizarRequest request, @PathVariable UUID estabelecimentoId){
        try {
            CategoriaResponse  atualizarCategoria = this.service.atualizarCategoria(request, estabelecimentoId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(atualizarCategoria);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping
    public ResponseEntity<CategoriaResponse> deletarCategoria(@RequestBody CategoriaDeletarRequest categoriaRequest){
        try{
            CategoriaResponse categoriaDelete = this.service.deletarCategoria(categoriaRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
