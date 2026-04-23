package br.com.vikfastfood.api.users.controller;

import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestNovaSenhaDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioResponseDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioResponseNovaSenhaDto;
import br.com.vikfastfood.api.users.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDto> cadastroUsuario(@RequestBody @Valid UsuarioRequestDto dto) {
        try {
            UsuarioResponseDto resposta  = this.usuarioService.cadastrar(dto);

            return  ResponseEntity.ok(resposta );
        }catch (Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/alterar-senha")
    public ResponseEntity<UsuarioResponseNovaSenhaDto> atualizarSenha(@RequestBody @Valid UsuarioRequestNovaSenhaDto dto) {
        try{
            UsuarioResponseNovaSenhaDto resposta = this.usuarioService.alterarSenha(dto);
            return  ResponseEntity.ok(resposta);
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> login(@RequestBody @Valid UsuarioRequestDto dto) {
        try {
            UsuarioResponseDto resposta = this.usuarioService.login(dto);
            return  ResponseEntity.ok(resposta);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
