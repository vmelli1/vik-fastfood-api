package br.com.vikfastfood.api.users.service;


import br.com.vikfastfood.api.users.dto.Usuario.*;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.model.UsuarioEstabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.repository.UsuarioRepository;
import br.com.vikfastfood.api.users.validation.Validar;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: criar exceção de domínio

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<Validar> validacoes;


    @Transactional
    public UsuarioResponseDto cadastrar(UsuarioCadastroRequest dto){
        //Validacoes de cadastro, exemplo : EMAIL - SENHAS ETC
        validacoes.forEach(v -> v.validar(dto));

        // Relacionamente entre o estabelecimento e usuarios
        Estabelecimento est = estabelecimentoRepository.findById(dto.estabelecimentoId()).orElseThrow(()-> new RuntimeException("Estabelecimento não encontrado"));
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        // cadastro Usuario, com Integracao do estabelicimento
        UsuarioEstabelecimento usuario = UsuarioEstabelecimento.builder()
                .email(dto.email())
                .senha(senhaCriptografada)
                .primeiroAcesso(true)
                .estabelecimento(est)
                .build();
        UsuarioEstabelecimento salvar = usuarioRepository.save(usuario);

        return  UsuarioResponseDto.builder()
                .email(salvar.getEmail())
                .primeiroAcesso(salvar.isPrimeiroAcesso())
                .build();
    }

    public UsuarioResponseDto login(UsuarioRequestDto dto){
        UsuarioEstabelecimento usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())){
            log.error("Tentativa de login com senha incorreta para o email: {}", dto.email());
            throw new RuntimeException("Credenciais inválidas");
        }

        return new UsuarioResponseDto(
                usuario.getEmail(),
                usuario.isPrimeiroAcesso(),
                usuario.getEstabelecimento().getId()
        );
    }

    @Transactional
    public UsuarioResponseNovaSenhaDto alterarSenha(UsuarioRequestNovaSenhaDto dto){
        UsuarioEstabelecimento usuario = usuarioRepository.findByEmail(dto.email())
            .orElseThrow(() -> new RuntimeException("Credencial inválida"));

        if(!usuario.isPrimeiroAcesso()){
            if(!passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha())){
                log.error("Tentativa de alteração de senha falhou para o usuário: {}", usuario.getEmail());
                throw new RuntimeException("Senha inválida");
            }
        }

        String novaSenha = passwordEncoder.encode(dto.novaSenha());
        usuario.setSenha(novaSenha);
        usuario.setPrimeiroAcesso(false);

        return new UsuarioResponseNovaSenhaDto(
                usuario.getEmail()
        );

    }
}
