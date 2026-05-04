package model;

import java.time.LocalDate;

/**
 * Classe modelo que representa a entidade Denuncia no sistema.
 */
public class Denuncia {

    /** Identificador único da denúncia */
    private int id;

    /** Descrição da denúncia */
    private String descricao;

    /** Data da denúncia */
    private LocalDate data;

    /** Indica se a denúncia é anônima */
    private boolean anonima;

    /** Identificador do usuário que fez a denúncia */
    private int usuarioId;

    /** Identificador da empresa denunciada */
    private int empresaId;

    /**
     * Construtor padrão.
     */
    public Denuncia() {
    }

    /**
     * @return o id da denúncia
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id da denúncia
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a descrição da denúncia
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao a descrição da denúncia
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return a data da denúncia
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data a data da denúncia
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * @return se é anônima
     */
    public boolean isAnonima() {
        return anonima;
    }

    /**
     * @param anonima se é anônima
     */
    public void setAnonima(boolean anonima) {
        this.anonima = anonima;
    }

    /**
     * @return o id do usuário
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param usuarioId o id do usuário
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * @return o id da empresa
     */
    public int getEmpresaId() {
        return empresaId;
    }

    /**
     * @param empresaId o id da empresa
     */
    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }
}
