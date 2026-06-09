package service;

import com.google.gson.Gson;
import dao.EmpresaDAO;
import model.Empresa;
import spark.Request;
import spark.Response;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

public class EmpresaService {

    private EmpresaDAO empresaDAO;
    private Gson gson;

    public EmpresaService() {
        this.empresaDAO = new EmpresaDAO();
        this.gson = new Gson();
    }

    public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = gson.fromJson(req.body(), Empresa.class);
            // nunca salva senha em texto puro
            empresa.setSenha(BCrypt.hashpw(empresa.getSenha(), BCrypt.gensalt()));
            empresaDAO.insert(empresa);
            return gson.toJson("{\"msg\":\"Empresa inserida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = gson.fromJson(req.body(), Empresa.class);
            empresa.setId(Integer.parseInt(req.params(":id")));
            empresaDAO.update(empresa);
            return gson.toJson("{\"msg\":\"Empresa atualizada com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            empresaDAO.remove(Integer.parseInt(req.params(":id")));
            return gson.toJson("{\"msg\":\"Empresa removida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = empresaDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(empresa);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(empresaDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String obterScorePublico(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            String nome = req.queryParams("nome");
            if (nome == null || nome.trim().isEmpty()) {
                res.status(400);
                return "{\"erro\":\"Parâmetro 'nome' é obrigatório\"}";
            }

            Empresa empresa = empresaDAO.buscarPorNome(nome);
            if (empresa == null) {
                res.status(404);
                return "{\"erro\":\"Empresa sem dados no SafeHer\",\"encontrada\":false,\"nome\":\"" + nome.replace("\"", "\\\"") + "\"}";
            }

            int totalAvaliacoes = 0;
            double somaNotas = 0.0;
            int totalDenuncias = 0;

            String sqlAval = "SELECT COUNT(*) as total, SUM(nota) as soma FROM Avaliacao WHERE empresa_id = ? OR (nome_empresa ILIKE ? AND (empresa_id IS NULL OR empresa_id = 0))";
            String sqlDenuncia = "SELECT COUNT(*) as total FROM Denuncia WHERE Empresa_id = ?";

            java.sql.Connection conn = null;
            try {
                empresaDAO.abrirConexao();
                conn = empresaDAO.getConexao();

                PreparedStatement stmtA = conn.prepareStatement(sqlAval);
                stmtA.setInt(1, empresa.getId());
                stmtA.setString(2, empresa.getNome());
                ResultSet rsA = stmtA.executeQuery();
                if (rsA.next()) {
                    totalAvaliacoes = rsA.getInt("total");
                    somaNotas = rsA.getDouble("soma");
                }
                rsA.close();
                stmtA.close();

                PreparedStatement stmtD = conn.prepareStatement(sqlDenuncia);
                stmtD.setInt(1, empresa.getId());
                ResultSet rsD = stmtD.executeQuery();
                if (rsD.next()) {
                    totalDenuncias = rsD.getInt("total");
                }
                rsD.close();
                stmtD.close();

            } finally {
                empresaDAO.fecharConexao();
            }

            double mediaAvaliacoes = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0.0;
            double baseScore = totalAvaliacoes > 0 ? mediaAvaliacoes * 20.0 : 100.0;
            double penalidade = totalDenuncias * 10.0;
            int scoreFinal = (int) Math.max(0, Math.min(100, baseScore - penalidade));

            String status = "Segura";
            String resumo = "Excelente reputação. Ambiente de trabalho altamente seguro e recomendado.";
            if (scoreFinal < 40) {
                status = "Crítica";
                resumo = "Nível de segurança muito baixo. Presença de denúncias graves e avaliações negativas.";
            } else if (scoreFinal < 70) {
                status = "Atenção";
                resumo = "Ambiente corporativo requer cuidados. Algumas reclamações ou notas medianas registradas.";
            }

            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("id", empresa.getId());
            payload.put("nome", empresa.getNome());
            payload.put("score", scoreFinal);
            payload.put("mediaAvaliacoes", mediaAvaliacoes);
            payload.put("totalAvaliacoes", totalAvaliacoes);
            payload.put("denuncias", totalDenuncias);
            payload.put("status", status);
            payload.put("resumo", resumo);
            payload.put("encontrada", true);

            return gson.toJson(payload);

        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    public String atualizarAssinatura(Request req, Response res) {
        res.type("application/json");
        try {
            java.util.Map<String, Object> body = gson.fromJson(req.body(), java.util.Map.class);
            Object idObj = body.get("empresaId");
            if (idObj == null) {
                res.status(400);
                return "{\"erro\":\"empresaId e plano são obrigatórios\"}";
            }
            int empresaId;
            if (idObj instanceof Double) {
                empresaId = ((Double) idObj).intValue();
            } else if (idObj instanceof Integer) {
                empresaId = (Integer) idObj;
            } else {
                empresaId = Integer.parseInt(idObj.toString().trim());
            }
            String plano = (String) body.get("plano");
            
            if (!plano.equals("Free") && !plano.equals("Premium") && !plano.equals("Plus")) {
                res.status(400);
                return "{\"erro\":\"Plano inválido\"}";
            }
            
            empresaDAO.atualizarPlano(empresaId, plano);
            
            Empresa empresa = empresaDAO.get(empresaId);
            return gson.toJson(empresa);
        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    private java.util.List<String> obterMotivosPorEmpresa(int empresaId) {
        java.util.List<String> motivos = new java.util.ArrayList<>();
        String sql = "SELECT tag, COUNT(*) as total FROM avaliacao_tag WHERE empresa_id = ? GROUP BY tag ORDER BY total DESC LIMIT 5";
        try {
            empresaDAO.abrirConexao();
            Connection conn = empresaDAO.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                motivos.add(rs.getString("tag"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao obter motivos: " + e.getMessage());
        } finally {
            try {
                empresaDAO.fecharConexao();
            } catch (Exception ex) {}
        }
        return motivos;
    }

    public String obterScorePublicoPorNome(Request req, Response res) {
        res.type("application/json");
        try {
            String nome = req.queryParams("nome");
            if (nome == null || nome.trim().isEmpty()) {
                res.status(400);
                return "{\"erro\":\"Parâmetro 'nome' é obrigatório\"}";
            }

            Empresa empresa = empresaDAO.buscarPorNome(nome);
            if (empresa == null) {
                return "{\"empresa\":\"" + nome.replace("\"", "\\\"") + "\",\"score\":0.0,\"quantidadeAvaliacoes\":0,\"motivos\":[],\"encontrada\":false,\"poweredBy\":\"SafeHer\"}";
            }

            int totalAvaliacoes = 0;
            double somaNotas = 0.0;

            String sqlAval = "SELECT COUNT(*) as total, SUM(nota) as soma FROM Avaliacao WHERE empresa_id = ? OR (nome_empresa ILIKE ? AND (empresa_id IS NULL OR empresa_id = 0))";

            try {
                empresaDAO.abrirConexao();
                Connection conn = empresaDAO.getConexao();
                PreparedStatement stmtA = conn.prepareStatement(sqlAval);
                stmtA.setInt(1, empresa.getId());
                stmtA.setString(2, empresa.getNome());
                ResultSet rsA = stmtA.executeQuery();
                if (rsA.next()) {
                    totalAvaliacoes = rsA.getInt("total");
                    somaNotas = rsA.getDouble("soma");
                }
                rsA.close();
                stmtA.close();
            } finally {
                empresaDAO.fecharConexao();
            }

            double mediaAvaliacoes = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0.0;
            mediaAvaliacoes = Math.round(mediaAvaliacoes * 10.0) / 10.0;

            java.util.List<String> motivos = obterMotivosPorEmpresa(empresa.getId());

            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("empresa", empresa.getNome());
            payload.put("score", mediaAvaliacoes);
            payload.put("quantidadeAvaliacoes", totalAvaliacoes);
            payload.put("motivos", motivos);
            payload.put("encontrada", true);
            payload.put("poweredBy", "SafeHer");

            return gson.toJson(payload);

        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    public String obterScorePublicoPorId(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            Empresa empresa = empresaDAO.get(id);
            if (empresa == null) {
                res.status(404);
                return "{\"erro\":\"Empresa nao encontrada\"}";
            }

            int totalAvaliacoes = 0;
            double somaNotas = 0.0;

            String sqlAval = "SELECT COUNT(*) as total, SUM(nota) as soma FROM Avaliacao WHERE empresa_id = ?";

            try {
                empresaDAO.abrirConexao();
                Connection conn = empresaDAO.getConexao();
                PreparedStatement stmtA = conn.prepareStatement(sqlAval);
                stmtA.setInt(1, empresa.getId());
                ResultSet rsA = stmtA.executeQuery();
                if (rsA.next()) {
                    totalAvaliacoes = rsA.getInt("total");
                    somaNotas = rsA.getDouble("soma");
                }
                rsA.close();
                stmtA.close();
            } finally {
                empresaDAO.fecharConexao();
            }

            double mediaAvaliacoes = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0.0;
            mediaAvaliacoes = Math.round(mediaAvaliacoes * 10.0) / 10.0;

            java.util.List<String> motivos = obterMotivosPorEmpresa(empresa.getId());

            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("empresa", empresa.getNome());
            payload.put("score", mediaAvaliacoes);
            payload.put("quantidadeAvaliacoes", totalAvaliacoes);
            payload.put("motivos", motivos);
            payload.put("poweredBy", "SafeHer");

            return gson.toJson(payload);

        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    public String buscarEmpresasPublico(Request req, Response res) {
        res.type("application/json");
        try {
            String nome = req.queryParams("nome");
            if (nome == null || nome.trim().isEmpty()) {
                res.status(400);
                return "{\"erro\":\"Parametro 'nome' e obrigatorio\"}";
            }

            java.util.List<java.util.Map<String, Object>> resultados = new java.util.ArrayList<>();
            String sql = "SELECT id, nome FROM Empresa WHERE nome ILIKE ? LIMIT 5";
            try {
                empresaDAO.abrirConexao();
                Connection conn = empresaDAO.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + nome.trim() + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int empresaId = rs.getInt("id");
                    String empresaNome = rs.getString("nome");

                    int totalAvaliacoes = 0;
                    double somaNotas = 0.0;
                    String sqlAval = "SELECT COUNT(*) as total, SUM(nota) as soma FROM Avaliacao WHERE empresa_id = ?";
                    PreparedStatement stmtA = conn.prepareStatement(sqlAval);
                    stmtA.setInt(1, empresaId);
                    ResultSet rsA = stmtA.executeQuery();
                    if (rsA.next()) {
                        totalAvaliacoes = rsA.getInt("total");
                        somaNotas = rsA.getDouble("soma");
                    }
                    rsA.close();
                    stmtA.close();

                    double mediaAvaliacoes = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0.0;
                    mediaAvaliacoes = Math.round(mediaAvaliacoes * 10.0) / 10.0;

                    java.util.Map<String, Object> item = new java.util.HashMap<>();
                    item.put("id", empresaId);
                    item.put("nome", empresaNome);
                    item.put("score", mediaAvaliacoes);
                    item.put("quantidadeAvaliacoes", totalAvaliacoes);
                    resultados.add(item);
                }
                rs.close();
                stmt.close();
            } finally {
                empresaDAO.fecharConexao();
            }

            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("parceiro", "Demo");
            payload.put("resultados", resultados);

            return gson.toJson(payload);

        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }
}
