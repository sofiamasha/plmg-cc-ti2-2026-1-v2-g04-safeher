package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados PostgreSQL.
 * Todas as classes DAO herdam desta classe.
 */
public class ConexaoDAO {

    /** URL de conexão com o banco de dados */
    private static final String URL = "jdbc:postgresql://localhost:5432/safeher";

    /** Usuário do banco de dados */
    private static final String USUARIO = "postgres";

    /** Senha do banco de dados */
    private static final String SENHA = "postgres";

    /** Objeto de conexão ativa */
    private Connection conexao;

    /**
     * Abre a conexão com o banco de dados.
     *
     * @return Connection objeto de conexão ativa
     * @throws SQLException em caso de erro na conexão
     */
    public Connection abrirConexao() throws SQLException {
        conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados.
     *
     * @throws SQLException em caso de erro ao fechar
     */
    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    /**
     * Retorna a conexão atual.
     *
     * @return Connection conexão ativa
     */
    public Connection getConexao() {
        return conexao;
    }
}
