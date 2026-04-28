package br.com.vikfastfood.api.users.validation;

import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestDto;
import br.com.vikfastfood.api.users.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmail implements Validar {
    private static final Logger log = LoggerFactory.getLogger(ValidacaoEmail.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(UsuarioRequestDto dto){
        boolean validarEmail = usuarioRepository.existsByEmail(dto.email());
        if (validarEmail){
            log.error("Usuario tem email ja cadastrado: {}", dto.email());
            throw new RuntimeException("Email já cadastrado");
        }
    }
}
