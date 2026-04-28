package br.com.vikfastfood.api.users.service;


import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestNovaSenhaDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioResponseDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioResponseNovaSenhaDto;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.model.Usuario;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.repository.UsuarioRepository;
import br.com.vikfastfood.api.users.validation.Validar;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private List<Validar> validar;

    public UsuarioService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Transactional
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        //Validacoes de cadastro, exemplo : EMAIL - SENHAS ETC
        validar.forEach(v -> v.validar(dto));

        // Relacionamente entre o estabelecimento e usuarios
        Estabelecimento est = estabelecimentoRepository.findById(dto.estabelecimentoId()).orElseThrow(()-> new RuntimeException("Estabelecimento não encontrado"));
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        // cadastro Usuario, com Integracao do estabelicimento
        Usuario usuario = Usuario.builder()
                .email(dto.email())
                .senha(senhaCriptografada)
                .primeiroAcesso(true)
                .estabelecimento(est)
                .build();
        est.getUsuarios().add(usuario);
        Usuario salvar = usuarioRepository.save(usuario);

        return  UsuarioResponseDto.builder()
                .email(salvar.getEmail())
                .primeiroAcesso(salvar.isPrimeiroAcesso())
                .build();
    }

    public UsuarioResponseDto login(UsuarioRequestDto dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())){
            log.error("Cliente inserindo a senha incorreta: {}", dto.senha());
            throw new RuntimeException("Credenciais inválidas");
        }

        return new UsuarioResponseDto(
                usuario.getEmail(),
                usuario.isPrimeiroAcesso()
        );
    }

    public UsuarioResponseNovaSenhaDto alterarSenha(UsuarioRequestNovaSenhaDto dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
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

        usuarioRepository.save(usuario);

        return new UsuarioResponseNovaSenhaDto(
                usuario.getEmail(),
                usuario.getSenha()
        );

    }
}
