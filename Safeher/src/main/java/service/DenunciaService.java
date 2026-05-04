package service;

import dao.DenunciaDAO;
import model.Denuncia;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Denuncia.
 */
public class DenunciaService {

    /** DAO utilizado para acesso ao banco de dados */
    private DenunciaDAO denunciaDAO;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public DenunciaService() {
        this.denunciaDAO = new DenunciaDAO();
    }

    /**
     * Cadastra uma nova denúncia.
     *
     * @param denuncia objeto Denuncia a ser cadastrado
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Denuncia denuncia) throws SQLException {
        denunciaDAO.insert(denuncia);
    }

    /**
     * Atualiza os dados de uma denúncia existente.
     *
     * @param denuncia objeto Denuncia com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Denuncia denuncia) throws SQLException {
        denunciaDAO.update(denuncia);
    }

    /**
     * Remove uma denúncia pelo ID.
     *
     * @param id identificador da denúncia
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        denunciaDAO.remove(id);
    }

    /**
     * Busca uma denúncia pelo ID.
     *
     * @param id identificador da denúncia
     * @return Denuncia encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Denuncia get(int id) throws SQLException {
        return denunciaDAO.get(id);
    }

    /**
     * Lista todas as denúncias cadastradas.
     *
     * @return List com todas as denúncias
     * @throws SQLException em caso de erro no banco
     */
    public List<Denuncia> listar() throws SQLException {
        return denunciaDAO.listar();
    }

    /**
     * Lista denúncias de um usuário específico.
     *
     * @param usuarioId identificador do usuário
     * @return List com as denúncias do usuário
     * @throws SQLException em caso de erro no banco
     */
    public List<Denuncia> listarPorUsuario(int usuarioId) throws SQLException {
        return denunciaDAO.listarPorUsuario(usuarioId);
    }
}
