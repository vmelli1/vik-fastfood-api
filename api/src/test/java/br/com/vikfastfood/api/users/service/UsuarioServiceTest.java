package br.com.vikfastfood.api.users.service;

import br.com.vikfastfood.api.users.controller.UsuarioController;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioCadastroRequest;
import br.com.vikfastfood.api.users.model.Estabelecimento;
import br.com.vikfastfood.api.users.model.UsuarioEstabelecimento;
import br.com.vikfastfood.api.users.repository.EstabelecimentoRepository;
import br.com.vikfastfood.api.users.repository.UsuarioRepository;
import br.com.vikfastfood.api.users.validation.Validar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Estabelecimento estabelecimento;

    @Spy
    private List<Validar> validacoes = new ArrayList<>();

    @Mock
    private Validar validar;

    @Mock
    private Validar validar2;

    private UsuarioCadastroRequest dto;

    @Captor
    private ArgumentCaptor<UsuarioEstabelecimento> usuarioEstabelecimentoCaptor;

    @Test
    void deveRetornarOkAoCriarUsuario() {
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(id);

        UsuarioEstabelecimento usuarioSalvo = new UsuarioEstabelecimento();
        usuarioSalvo.setEmail("Teste@teste.com");
        usuarioSalvo.setSenha("senha-criptografada");
        usuarioSalvo.setPrimeiroAcesso(true);
        usuarioSalvo.setEstabelecimento(estabelecimento);


        this.dto = new UsuarioCadastroRequest("Teste@teste.com", "teste", id);
        given(estabelecimentoRepository.findById(dto.estabelecimentoId())).willReturn(Optional.of(estabelecimento));
        given(usuarioRepository.save(any(UsuarioEstabelecimento.class)))
                .willReturn(usuarioSalvo); // ← aqui resolve o NPE
        given(passwordEncoder.encode(dto.senha())).willReturn("senha-criptografada");

        usuarioService.cadastrar(dto);

        then(usuarioRepository).should().save(usuarioEstabelecimentoCaptor.capture());
        UsuarioEstabelecimento usuarioEstabelecimento = usuarioEstabelecimentoCaptor.getValue();

        assertThat(usuarioEstabelecimento.getEmail()).isEqualTo(dto.email());
        assertThat(usuarioEstabelecimento.isPrimeiroAcesso()).isTrue();
        assertThat(usuarioEstabelecimento.getEstabelecimento()).isEqualTo(estabelecimento);
        assertThat(usuarioEstabelecimento.getSenha()).isEqualTo("senha-criptografada") ;// senha foi criptografada

    }
}