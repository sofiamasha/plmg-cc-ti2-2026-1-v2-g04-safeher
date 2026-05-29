package service;

import com.google.gson.Gson;
import dao.AvaliacaoDAO;
import dao.EmpresaDAO;
import model.Avaliacao;
import model.Empresa;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvaliacaoService {

    private AvaliacaoDAO avaliacaoDAO;
    private EmpresaDAO empresaDAO; // Instanciado para atualizar o índice da empresa
    private Gson gson;

    public AvaliacaoService() {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.empresaDAO = new EmpresaDAO();
        this.gson = new Gson();
    }

    // Função auxiliar para respostas JSON limpas
    private String jsonResposta(String chave, String mensagem) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put(chave, mensagem);
        return gson.toJson(resposta);
    }

    public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
            
            // 1. Salva a avaliação recebida
            avaliacaoDAO.insert(avaliacao);
            
            // 2. Se houver uma empresa associada, recalcula a média das estrelas
            if (avaliacao.getEmpresaId() != 0) {
                atualizarIndiceEmpresa(avaliacao.getEmpresaId());
            }

            res.status(201);
            return jsonResposta("msg", "Avaliacao inserida com sucesso");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    // Função que calcula a média matemática e salva na Empresa
    private void atualizarIndiceEmpresa(int empresaId) throws Exception {
        List<Avaliacao> avaliacoes = avaliacaoDAO.listarPorEmpresa(empresaId);
        
        if (avaliacoes != null && !avaliacoes.isEmpty()) {
            double soma = 0;
            for (Avaliacao av : avaliacoes) {
                soma += av.getNota();
            }
            double media = soma / avaliacoes.size();
            
            // Converte e arredonda para 2 casas decimais (ex: 3.50)
            BigDecimal novoIndice = new BigDecimal(media).setScale(2, RoundingMode.HALF_UP);
            
            // Busca a empresa atual, muda o índice e atualiza no banco
            Empresa empresa = empresaDAO.get(empresaId);
            if (empresa != null) {
                empresa.setIndice(novoIndice);
                empresaDAO.update(empresa);
            }
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
            avaliacao.setId(Integer.parseInt(req.params(":id")));
            avaliacaoDAO.update(avaliacao);
            
            // Recalcula se o update mudar a nota
            if (avaliacao.getEmpresaId() != 0) {
                atualizarIndiceEmpresa(avaliacao.getEmpresaId());
            }
            
            return jsonResposta("msg", "Avaliacao atualizada com sucesso");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            
            // Descobre qual era a empresa antes de apagar para poder atualizar a média depois
            Avaliacao avaliacao = avaliacaoDAO.get(id);
            int empresaId = (avaliacao != null) ? avaliacao.getEmpresaId() : 0;
            
            avaliacaoDAO.remove(id);
            
            // Atualiza a média após a remoção
            if (empresaId != 0) {
                atualizarIndiceEmpresa(empresaId);
            }
            
            return jsonResposta("msg", "Avaliacao removida com sucesso");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = avaliacaoDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(avaliacao);
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(avaliacaoDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String listarPorEmpresa(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(avaliacaoDAO.listarPorEmpresa(Integer.parseInt(req.params(":empresaId"))));
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }
}