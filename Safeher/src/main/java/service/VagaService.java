package service;

import com.google.gson.Gson;
import dao.VagaDAO;
import model.Vaga;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VagaService {

    private VagaDAO vagaDAO;
    private Gson gson;

    public VagaService() {
        this.vagaDAO = new VagaDAO();
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
            Vaga vaga = gson.fromJson(req.body(), Vaga.class);
            vagaDAO.insert(vaga);
            res.status(201);
            return jsonResposta("msg", "Vaga publicada com sucesso!");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            vagaDAO.remove(id);
            res.status(200);
            return jsonResposta("msg", "Vaga removida com sucesso!");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            Vaga vaga = vagaDAO.get(id);
            if (vaga == null) {
                res.status(404);
                return jsonResposta("erro", "Vaga nao encontrada.");
            }
            return gson.toJson(vaga);
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            List<Vaga> lista = vagaDAO.listar();
            return gson.toJson(lista);
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String listarPorEmpresa(Request req, Response res) {
        res.type("application/json");
        try {
            int empresaId = Integer.parseInt(req.params(":empresaId"));
            List<Vaga> lista = vagaDAO.listarPorEmpresa(empresaId);
            return gson.toJson(lista);
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }
}
