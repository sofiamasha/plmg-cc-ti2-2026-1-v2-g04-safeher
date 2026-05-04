package model;

/**
 * Classe modelo que representa a entidade Avaliacao no sistema.
 */
public class Avaliacao {

    /** Identificador único da avaliação */
    private int id;

    /** Comentário da avaliação */
    private String comentario;

    /** Nota da avaliação */
    private int nota;

    /** Identificador do usuário que fez a avaliação */
    private int usuarioId;

    /**
     * Construtor padrão.
     */
    public Avaliacao() {
    }

    /**
     * @return o id da avaliação
     */
    public int getId() {
        return id;
    }

    /**
     * @param id o id da avaliação
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return o comentário da avaliação
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario o comentário da avaliação
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return a nota da avaliação
     */
    public int getNota() {
        return nota;
    }

    /**
     * @param nota a nota da avaliação
     */
    public void setNota(int nota) {
        this.nota = nota;
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
}
