package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String URL =
            System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/safeher");

    private static final String USUARIO =
            System.getenv().getOrDefault("DB_USER", "postgres");

    private static final String SENHA =
            System.getenv().getOrDefault("DB_PASSWORD", "postgres");

    private Connection conexao;

    public Connection abrirConexao() throws SQLException {
        conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        return conexao;
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}