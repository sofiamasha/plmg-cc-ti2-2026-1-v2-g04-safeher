package dao;

import model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO responsável pelas operações de banco de dados da entidade Empresa.
 */
public class EmpresaDAO extends ConexaoDAO {

    /**
     * Insere uma nova empresa no banco de dados.
     *
     * @param empresa objeto Empresa a ser inserido
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (id, nome, indice) VALUES (?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresa.getId());
            stmt.setString(2, empresa.getNome());
            stmt.setBigDecimal(3, empresa.getIndice());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Atualiza os dados de uma empresa existente.
     *
     * @param empresa objeto Empresa com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome=?, indice=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, empresa.getNome());
            stmt.setBigDecimal(2, empresa.getIndice());
            stmt.setInt(3, empresa.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Remove uma empresa pelo ID.
     *
     * @param id identificador da empresa a ser removida
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Empresa WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Busca uma empresa pelo ID.
     *
     * @param id identificador da empresa
     * @return Empresa encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Empresa get(int id) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE id=?";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setIndice(rs.getBigDecimal("indice"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }

    /**
     * Lista todas as empresas cadastradas.
     *
     * @return List com todas as empresas
     * @throws SQLException em caso de erro no banco
     */
    public List<Empresa> listar() throws SQLException {
        String sql = "SELECT * FROM Empresa";
        List<Empresa> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setIndice(rs.getBigDecimal("indice"));
                lista.add(empresa);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
