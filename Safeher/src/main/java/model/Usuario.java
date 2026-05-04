package model;

import java.time.LocalDate;

/**
 * Classe modelo que representa a entidade Usuario no sistema.
 */
public class Usuario {

    /** Identificador único do usuário */
    private int id;

    /** Nome completo do usuário */
    private String nome;

    /** E-mail do usuário */
    private String email;

    /** Senha do usuário */
    private String senha;

    /** Data de registro do usuário */
    private LocalDate data;

    /**
     * Construtor padrão.
     */
    public Usuario() {
    }

    /**
     * @return o id do usuário
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id do usuário
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return o nome do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome o nome do usuário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return o e-mail do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email o e-mail do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return a senha do usuário
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha a senha do usuário
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return a data de registro
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data a data de registro
     */
    public void setData(LocalDate data) {
        this.data = data;
    }
}
