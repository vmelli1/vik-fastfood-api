package br.com.vikfastfood.api.users.validation;

import br.com.vikfastfood.api.users.dto.Usuario.UsuarioCadastroRequest;
import br.com.vikfastfood.api.users.dto.Usuario.UsuarioRequestDto;

public interface Validar {
    void validar(UsuarioCadastroRequest usuarioCadastroRequestDto);
}
