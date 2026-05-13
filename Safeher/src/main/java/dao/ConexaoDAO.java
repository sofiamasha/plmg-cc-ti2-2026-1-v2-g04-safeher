package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/safeher";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";
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
