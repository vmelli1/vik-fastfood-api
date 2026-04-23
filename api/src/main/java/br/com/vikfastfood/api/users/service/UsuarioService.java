package br.com.vikfastfood.api.users.service;


import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestNovaSenhaDto;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioResponseDto;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.model.Usuario;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Transactional
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        if (usuarioRepository.existsByEmail(dto.email())){
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        Estabelecimento est = estabelecimentoRepository.findById(dto.estabelecimentoId()).orElseThrow(()-> new RuntimeException("Estabelecimento não encontrado"));

        usuario.setEmail(dto.email());
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        usuario.setSenha(senhaCriptografada);
        usuario.setPrimeiroAcesso(true);
        usuario.setEstabelecimento(est);
        Usuario salvar = usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                salvar.getEmail(),
                salvar.isPrimeiroAcesso()
        );
    }

    public UsuarioResponseDto login(UsuarioRequestDto dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())){
            throw new RuntimeException("Credenciais inválidas");
        }

        return new UsuarioResponseDto(
                usuario.getEmail(),
                usuario.isPrimeiroAcesso()
        );
    }

    public void alterarSenha(UsuarioRequestNovaSenhaDto dto){
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

    }
}
