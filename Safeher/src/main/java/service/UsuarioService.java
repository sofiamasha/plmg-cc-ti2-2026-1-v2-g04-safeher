package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Usuario.
 */
public class UsuarioService {

    /** DAO utilizado para acesso ao banco de dados */
    private UsuarioDAO usuarioDAO;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Cadastra um novo usuário.
     *
     * @param usuario objeto Usuario a ser cadastrado
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Usuario usuario) throws SQLException {
        usuarioDAO.insert(usuario);
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario objeto Usuario com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Usuario usuario) throws SQLException {
        usuarioDAO.update(usuario);
    }

    /**
     * Remove um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        usuarioDAO.remove(id);
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return Usuario encontrado ou null
     * @throws SQLException em caso de erro no banco
     */
    public Usuario get(int id) throws SQLException {
        return usuarioDAO.get(id);
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return List com todos os usuários
     * @throws SQLException em caso de erro no banco
     */
    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.listar();
    }
}
