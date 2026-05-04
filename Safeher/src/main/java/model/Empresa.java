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

    /**
     * Construtor padrão.
     */
    public Empresa() {
    }

    /**
     * @return o id da empresa
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id da empresa
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return o nome da empresa
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome o nome da empresa
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return o índice da empresa
     */
    public BigDecimal getIndice() {
        return indice;
    }

    /**
     * @param indice o índice da empresa
     */
    public void setIndice(BigDecimal indice) {
        this.indice = indice;
    }
}
