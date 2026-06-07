package service;

import com.google.gson.Gson;
import dao.CandidaturaDAO;
import dao.EmpresaDAO;
import dao.VagaDAO;
import model.Candidatura;
import model.Empresa;
import model.Vaga;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidaturaService {

    private CandidaturaDAO candidaturaDAO;
    private VagaDAO vagaDAO;
    private EmpresaDAO empresaDAO;
    private Gson gson;

    public CandidaturaService() {
        this.candidaturaDAO = new CandidaturaDAO();
        this.vagaDAO = new VagaDAO();
        this.empresaDAO = new EmpresaDAO();
        this.gson = new Gson();
    }

    private String jsonResposta(String chave, String mensagem) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put(chave, mensagem);
        return gson.toJson(resposta);
    }

    public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Candidatura c = gson.fromJson(req.body(), Candidatura.class);
            candidaturaDAO.insert(c);
            res.status(201);
            return jsonResposta("msg", "Candidatura registrada com sucesso!");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String listarPorVaga(Request req, Response res) {
        res.type("application/json");
        try {
            int vagaId = Integer.parseInt(req.params(":vagaId"));
            
            // 1. Obter a vaga para descobrir qual empresa a criou
            Vaga vaga = vagaDAO.get(vagaId);
            if (vaga == null) {
                res.status(404);
                return jsonResposta("erro", "Vaga não encontrada.");
            }
            
            // 2. Obter a empresa para verificar o plano
            Empresa empresa = empresaDAO.get(vaga.getEmpresaId());
            if (empresa == null) {
                res.status(404);
                return jsonResposta("erro", "Empresa proprietária da vaga não encontrada.");
            }
            
            // 3. Validar se o plano é Premium no Backend/Banco de Dados (Removido para permitir triagem de candidatas por qualquer empresa)
            /*
            if (empresa.getPlano() == null || !empresa.getPlano().equalsIgnoreCase("Premium")) {
                res.status(403); // Forbidden
                return jsonResposta("erro", "Acesso negado: Empresas no plano FREE não podem acessar candidatas.");
            }
            */
            
            // 4. Se for Premium, retornar a lista de candidatas
            List<Candidatura> lista = candidaturaDAO.listarPorVaga(vagaId);
            return gson.toJson(lista);
            
        } catch (NumberFormatException e) {
            res.status(400);
            return jsonResposta("erro", "ID da vaga inválido.");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }
}
