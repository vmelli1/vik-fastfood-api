package br.com.vikfastfood.api.users.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.models.annotations.internal.ColumnsAnnotation;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition  = "BINARY(16)")
    private UUID id;
    @lombok.Setter
    @Column(nullable = false)
    private String email;
    @lombok.Setter
    @Column(nullable = false)
    private String senha;
    private boolean primeiroAcesso;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }
        this.email = email.trim().toLowerCase();
        validarEmail();
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        this.senha = senha;
        validarSenha();
    }


    // ============= metodos auxiliares para validar o email e senha =============
     private void validarEmail() {
         String emailRegex = "^[^@\\s]+@[^@\\s.]+\\.[^@\\s.]+$";
         if (!email.matches(emailRegex)) {
             throw new IllegalArgumentException("Email inválido.");
         }

     }

     private void validarSenha(){
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve conter pelo menos 6 caracteres.");
        }
     }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Usuario usuario = (Usuario) object;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
