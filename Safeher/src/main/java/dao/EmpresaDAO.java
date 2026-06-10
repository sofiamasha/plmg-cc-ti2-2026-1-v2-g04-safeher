package dao;

import model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO extends ConexaoDAO {

    public EmpresaDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        String sql1 = "ALTER TABLE Empresa ADD COLUMN IF NOT EXISTS plano VARCHAR(20) DEFAULT 'Free'";
        String sql2 = "ALTER TABLE Empresa ADD COLUMN IF NOT EXISTS foto TEXT";
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Empresa: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void atualizarPlano(int id, String plano) throws SQLException {
        String sql = "UPDATE Empresa SET plano=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, plano);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void insert(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (id, nome, indice, cnpj, cep, endereco, email, telefone, senha, plano, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresa.getId());
            stmt.setString(2, empresa.getNome());
            stmt.setBigDecimal(3, empresa.getIndice());
            stmt.setString(4, empresa.getCnpj());
            stmt.setString(5, empresa.getCep());
            stmt.setString(6, empresa.getEndereco());
            stmt.setString(7, empresa.getEmail());
            stmt.setString(8, empresa.getTelefone());
            stmt.setString(9, empresa.getSenha());
            stmt.setString(10, empresa.getPlano() != null ? empresa.getPlano() : "Free");
            stmt.setString(11, empresa.getFoto());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome=?, indice=?, cnpj=?, cep=?, endereco=?, email=?, telefone=?, foto=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, empresa.getNome());
            stmt.setBigDecimal(2, empresa.getIndice());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getCep());
            stmt.setString(5, empresa.getEndereco());
            stmt.setString(6, empresa.getEmail());
            stmt.setString(7, empresa.getTelefone());
            stmt.setString(8, empresa.getFoto());
            stmt.setInt(9, empresa.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

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
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCep(rs.getString("cep"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setEmail(rs.getString("email"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setPlano(rs.getString("plano"));
                empresa.setFoto(rs.getString("foto"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }

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
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCep(rs.getString("cep"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setEmail(rs.getString("email"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setPlano(rs.getString("plano"));
                empresa.setFoto(rs.getString("foto"));
                lista.add(empresa);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public Empresa buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT id, nome FROM Empresa WHERE nome ILIKE ? LIMIT 1";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, "%" + nome.trim() + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }

    // Uso interno do login da empresa: traz a senha (hash) para comparar com BCrypt.
    public Empresa buscarPorNomeLogin(String nome) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE nome = ?";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setIndice(rs.getBigDecimal("indice"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCep(rs.getString("cep"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setEmail(rs.getString("email"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setSenha(rs.getString("senha"));
                empresa.setPlano(rs.getString("plano"));
                empresa.setFoto(rs.getString("foto"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }



     // Busca empresa por nome com correspondencia exata (case-insensitive).
    public Empresa buscarPorNomeExato(String nome) throws SQLException {
        String sql = "SELECT id, nome FROM Empresa WHERE LOWER(nome) = LOWER(?)";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, nome.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }

    // Cria um registro minimo de empresa (auto-cadastro a partir de uma denuncia)
    // e devolve o id gerado. Usa MAX+1 para ser compativel com o id manual legado.
    public int criarMinima(String nome) throws SQLException {
        int novoId;
        try {
            abrirConexao();
            PreparedStatement maxStmt = getConexao().prepareStatement(
                    "SELECT COALESCE(MAX(id), 0) + 1 AS prox FROM Empresa");
            ResultSet rsMax = maxStmt.executeQuery();
            rsMax.next();
            novoId = rsMax.getInt("prox");
            rsMax.close();
            maxStmt.close();

            PreparedStatement stmt = getConexao().prepareStatement(
                    "INSERT INTO Empresa (id, nome, plano) VALUES (?, ?, 'Free')");
            stmt.setInt(1, novoId);
            stmt.setString(2, nome.trim());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return novoId;
    }
}
