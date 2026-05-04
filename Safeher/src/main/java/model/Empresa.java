package model;

import java.math.BigDecimal;

/**
 * Classe modelo que representa a entidade Empresa no sistema.
 */
public class Empresa {

    /** Identificador único da empresa */
    private int id;

    /** Nome da empresa */
    private String nome;

    /** Índice de avaliação da empresa */
    private BigDecimal indice;
    private String cnpj;
    private String cep;
    private String endereco;
    private String email;
    private String telefone;
    private String senha;

    public Empresa() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getIndice() { return indice; }
    public void setIndice(BigDecimal indice) { this.indice = indice; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
