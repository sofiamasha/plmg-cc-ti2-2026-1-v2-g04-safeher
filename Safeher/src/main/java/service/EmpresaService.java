package service;

import dao.EmpresaDAO;
import model.Empresa;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço responsável pelas regras de negócio da entidade Empresa.
 */
public class EmpresaService {

    /** DAO utilizado para acesso ao banco de dados */
    private EmpresaDAO empresaDAO;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public EmpresaService() {
        this.empresaDAO = new EmpresaDAO();
    }

    /**
     * Cadastra uma nova empresa.
     *
     * @param empresa objeto Empresa a ser cadastrado
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Empresa empresa) throws SQLException {
        empresaDAO.insert(empresa);
    }

    /**
     * Atualiza os dados de uma empresa existente.
     *
     * @param empresa objeto Empresa com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Empresa empresa) throws SQLException {
        empresaDAO.update(empresa);
    }

    /**
     * Remove uma empresa pelo ID.
     *
     * @param id identificador da empresa
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        empresaDAO.remove(id);
    }

    /**
     * Busca uma empresa pelo ID.
     *
     * @param id identificador da empresa
     * @return Empresa encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Empresa get(int id) throws SQLException {
        return empresaDAO.get(id);
    }

    /**
     * Lista todas as empresas cadastradas.
     *
     * @return List com todas as empresas
     * @throws SQLException em caso de erro no banco
     */
    public List<Empresa> listar() throws SQLException {
        return empresaDAO.listar();
    }
}
