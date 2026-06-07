package dao;

import model.Candidatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidaturaDAO extends ConexaoDAO {

    public CandidaturaDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        String sql = "CREATE TABLE IF NOT EXISTS Candidatura (" +
                     "    id SERIAL PRIMARY KEY," +
                     "    vaga_id INT," +
                     "    candidata_id INT," +
                     "    candidata_nome VARCHAR(100)," +
                     "    candidata_email VARCHAR(100)," +
                     "    candidata_telefone VARCHAR(50)," +
                     "    candidata_foto TEXT," +
                     "    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                     "    FOREIGN KEY (vaga_id) REFERENCES Vaga(id) ON DELETE CASCADE" +
                     ")";
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Candidatura: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void insert(Candidatura c) throws SQLException {
        String sql = "INSERT INTO Candidatura (vaga_id, candidata_id, candidata_nome, candidata_email, candidata_telefone, candidata_foto, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, c.getVagaId());
            stmt.setInt(2, c.getCandidataId());
            stmt.setString(3, c.getCandidataNome());
            stmt.setString(4, c.getCandidataEmail());
            stmt.setString(5, c.getCandidataTelefone());
            stmt.setString(6, c.getCandidataFoto());
            if (c.getData() != null) {
                try {
                    // Try to parse ISO 8601 string
                    stmt.setTimestamp(7, Timestamp.from(java.time.Instant.parse(c.getData())));
                } catch (Exception parseEx) {
                    try {
                        stmt.setTimestamp(7, Timestamp.valueOf(c.getData().replace("T", " ").substring(0, 19)));
                    } catch (Exception e) {
                        stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    }
                }
            } else {
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public List<Candidatura> listarPorVaga(int vagaId) throws SQLException {
        String sql = "SELECT * FROM Candidatura WHERE vaga_id=? ORDER BY id DESC";
        List<Candidatura> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, vagaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Candidatura c = new Candidatura();
                c.setId(rs.getInt("id"));
                c.setVagaId(rs.getInt("vaga_id"));
                c.setCandidataId(rs.getInt("candidata_id"));
                c.setCandidataNome(rs.getString("candidata_nome"));
                c.setCandidataEmail(rs.getString("candidata_email"));
                c.setCandidataTelefone(rs.getString("candidata_telefone"));
                c.setCandidataFoto(rs.getString("candidata_foto"));
                
                Timestamp ts = rs.getTimestamp("data");
                if (ts != null) {
                    c.setData(ts.toInstant().toString());
                }
                lista.add(c);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
