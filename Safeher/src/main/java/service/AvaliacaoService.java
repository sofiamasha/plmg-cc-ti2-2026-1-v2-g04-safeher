package service;

import dao.AvaliacaoDAO;
import model.Avaliacao;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Avaliacao.
 */
public class AvaliacaoService {

    /** DAO utilizado para acesso ao banco de dados */
    private AvaliacaoDAO avaliacaoDAO;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public AvaliacaoService() {
        this.avaliacaoDAO = new AvaliacaoDAO();
    }

    /**
     * Cadastra uma nova avaliação.
     *
     * @param avaliacao objeto Avaliacao a ser cadastrado
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Avaliacao avaliacao) throws SQLException {
        avaliacaoDAO.insert(avaliacao);
    }

    /**
     * Atualiza os dados de uma avaliação existente.
     *
     * @param avaliacao objeto Avaliacao com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Avaliacao avaliacao) throws SQLException {
        avaliacaoDAO.update(avaliacao);
    }

    /**
     * Remove uma avaliação pelo ID.
     *
     * @param id identificador da avaliação
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        avaliacaoDAO.remove(id);
    }

    /**
     * Busca uma avaliação pelo ID.
     *
     * @param id identificador da avaliação
     * @return Avaliacao encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Avaliacao get(int id) throws SQLException {
        return avaliacaoDAO.get(id);
    }

    /**
     * Lista todas as avaliações cadastradas.
     *
     * @return List com todas as avaliações
     * @throws SQLException em caso de erro no banco
     */
    public List<Avaliacao> listar() throws SQLException {
        return avaliacaoDAO.listar();
    }

    public List<Avaliacao> listarPorEmpresa(int empresaId) throws SQLException {
        return avaliacaoDAO.listarPorEmpresa(empresaId);
    }
}
