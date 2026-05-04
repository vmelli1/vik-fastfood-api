package br.com.vikfastfood.api.users.model;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "estabelecimentos")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "nome", nullable = false) // nullable = false para garantir que o nome seja obrigatório
    private String nome;
    @lombok.Setter
    @Column (name = "cnpj", nullable = false, unique = true) // unique = true para garantir que o CNPJ seja único
    private String cnpj;
    @Column(name = "whatsapp", nullable = false)
    private String whatsapp;

    private String urlLogo;
    @Column (name = "endereco", nullable = false)
    private String endereco;
    private BigDecimal taxaEntrega;


    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true) // orphanRemoval para garantir que os usuários sejam removidos quando o estabelecimento for deletado
    List<UsuarioEstabelecimento> usuarios = new ArrayList<>();
    @OneToMany(mappedBy = "estabelecimento",  cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProdutoEstabelecimento> produtos = new ArrayList<>();

    public void setNome(String nome) {
        this.nome = nome;
        validarNome();
    }

    public void setCnpj(String cnpj) {
        if(cnpj == null || cnpj.isEmpty()) {
          throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
        // Remove caracteres não numéricos
        this.cnpj = cnpj.replaceAll("\\D", "");
        validacaoCnpj();
    }

    // ============= metodo auxiliares para validar o CNPJ =============

    private void validacaoCnpj(){
        if(this.cnpj == null || this.cnpj.trim().isEmpty()){
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
        validarTamanhoCnpj();
        digitosRepetidosCnpj();
        validarVerificadores();
    }

    private void validarTamanhoCnpj(){
       String regexCnpj = "\\d{14}";
       if(!this.cnpj.matches(regexCnpj)){ //
              throw new IllegalArgumentException("CNPJ inválido. O CNPJ deve conter exatamente 14 dígitos numéricos.");
       }
    }
    private void digitosRepetidosCnpj(){
        char digitosRepetidos = this.cnpj.charAt(0);
        for( int i = 1; i < this.cnpj.length(); i++){
            if(this.cnpj.charAt(i) != digitosRepetidos){
                return; // Se encontrar um dígito diferente, sai do método
            }
        }
        throw new IllegalArgumentException("CNPJ inválido. O CNPJ não pode conter todos os dígitos iguais.");
    }

    private void validarVerificadores(){
    int [] multiplicadoresPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    int [] multiplicadoresSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    int soma = 0;
    for ( int i = 0; i < 12; i++){
        soma = soma + (this.cnpj.charAt(i) - '0') * multiplicadoresPrimeiroDigito[i];
    }

    int resto = soma % 11;
    char digito1 = (resto < 2 ) ? '0' : (char)((11 - resto) + '0');
    if(digito1 != this.cnpj.charAt(12)){
        throw new IllegalArgumentException("CNPJ inválido. O primeiro dígito verificador está incorreto.");
    }
    soma = 0;
    for ( int i = 0; i < 13; i++){
        soma = soma + (this.cnpj.charAt(i) - '0') * multiplicadoresSegundoDigito[i];
    }

    int resto2 = soma % 11;
    char digito2 = (resto2 < 2 ) ? '0' : (char)((11 - resto2) + '0');
    if(digito2 != this.cnpj.charAt(13)){
        throw new IllegalArgumentException("CNPJ inválido. O segundo dígito verificador está incorreto.");
    }
    }

    // ============= metodo auxiliar para validar o nome do estabelecimento =============

    private void validarNome(){
        if (this.nome == null || this.nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do estabelecimento é obrigatório.");
        }
        if(this.nome.length() < 3 || this.nome.length() > 100) {
            throw new IllegalArgumentException("O nome do estabelecimento deve ter entre 3 e 100 caracteres.");
        }
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Estabelecimento that = (Estabelecimento) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
