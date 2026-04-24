package br.com.vikfastfood.api.users.controller;

import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoRequestDto;
import br.com.vikfastfood.api.users.dto.estabelecimento.EstabelecimentoResponseDto;
import br.com.vikfastfood.api.users.service.EstabelecimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("estabelecimentos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EstabelecimentoController {
    @Autowired
    private EstabelecimentoService estabelecimentoService;


    @PostMapping
    public ResponseEntity<EstabelecimentoResponseDto> cadastroEstabelecimento(@RequestBody @Valid EstabelecimentoRequestDto dto) {
        try{
            EstabelecimentoResponseDto resposta = this.estabelecimentoService.cadastrar(dto);
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
