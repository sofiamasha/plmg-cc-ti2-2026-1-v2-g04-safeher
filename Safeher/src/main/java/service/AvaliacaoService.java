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
            
            // Se o empresaId for 0 ou null, tenta buscar por nome para associar
            if (avaliacao.getEmpresaId() == 0 && avaliacao.getNomeEmpresa() != null) {
                Empresa emp = empresaDAO.buscarPorNome(avaliacao.getNomeEmpresa());
                if (emp != null) {
                    avaliacao.setEmpresaId(emp.getId());
                }
            }

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
        Empresa empresa = empresaDAO.get(empresaId);
        if (empresa != null) {
            // Auto-associa avaliações órfãs com o mesmo nome para manter consistência
            avaliacaoDAO.associarEmpresaIdPorNome(empresaId, empresa.getNome());
        }

        List<Avaliacao> avaliacoes = avaliacaoDAO.listarPorEmpresa(empresaId);
        
        if (avaliacoes != null && !avaliacoes.isEmpty()) {
            double soma = 0;
            for (Avaliacao av : avaliacoes) {
                soma += av.getNota();
            }
            double media = soma / avaliacoes.size();
            
            // Converte e arredonda para 2 casas decimais (ex: 3.50)
            BigDecimal novoIndice = new BigDecimal(media).setScale(2, RoundingMode.HALF_UP);
            
            if (empresa != null) {
                empresa.setIndice(novoIndice);
                empresaDAO.update(empresa);
            }
        } else {
            if (empresa != null) {
                empresa.setIndice(new BigDecimal("0.00"));
                empresaDAO.update(empresa);
            }
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
            avaliacao.setId(Integer.parseInt(req.params(":id")));

            // Se o empresaId for 0 ou null, tenta buscar por nome para associar
            if (avaliacao.getEmpresaId() == 0 && avaliacao.getNomeEmpresa() != null) {
                Empresa emp = empresaDAO.buscarPorNome(avaliacao.getNomeEmpresa());
                if (emp != null) {
                    avaliacao.setEmpresaId(emp.getId());
                }
            }

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
            
            System.out.println("SafeHer Deletion Log: Deletando avaliacao id=" + id + ", encontrada? " + (avaliacao != null) + ", empresaId=" + empresaId);
            
            avaliacaoDAO.remove(id);
            
            // Atualiza a média após a remoção
            if (empresaId != 0) {
                atualizarIndiceEmpresa(empresaId);
            }
            
            return jsonResposta("msg", "Avaliacao removida com sucesso");
        } catch (Exception e) {
            System.err.println("SafeHer Deletion Error: " + e.getMessage());
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

    public String salvarResposta(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            
            // Validar se avaliação existe
            Avaliacao aval = avaliacaoDAO.get(id);
            if (aval == null) {
                res.status(404);
                return jsonResposta("erro", "Avaliação não encontrada.");
            }

            // Validar se a empresa possui plano Plus ou Premium
            Empresa empresa = empresaDAO.get(aval.getEmpresaId());
            if (empresa == null) {
                res.status(404);
                return jsonResposta("erro", "Empresa associada não encontrada.");
            }
            if (empresa.getPlano() == null || (!empresa.getPlano().equalsIgnoreCase("Plus") && !empresa.getPlano().equalsIgnoreCase("Premium"))) {
                res.status(403);
                return jsonResposta("erro", "Acesso negado: Respostas oficiais a avaliações são exclusivas dos planos Plus/Premium.");
            }

            Map<String, String> body = gson.fromJson(req.body(), Map.class);
            String resposta = body.get("resposta");
            if (resposta == null) {
                res.status(400);
                return jsonResposta("erro", "O campo 'resposta' é obrigatório.");
            }
            
            avaliacaoDAO.atualizarResposta(id, resposta);
            res.status(200);
            return jsonResposta("msg", "Resposta oficial cadastrada com sucesso!");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }

    public String excluirResposta(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));

            // Validar se avaliação existe
            Avaliacao aval = avaliacaoDAO.get(id);
            if (aval == null) {
                res.status(404);
                return jsonResposta("erro", "Avaliação não encontrada.");
            }

            // Validar se a empresa possui plano Plus ou Premium
            Empresa empresa = empresaDAO.get(aval.getEmpresaId());
            if (empresa == null) {
                res.status(404);
                return jsonResposta("erro", "Empresa associada não encontrada.");
            }
            if (empresa.getPlano() == null || (!empresa.getPlano().equalsIgnoreCase("Plus") && !empresa.getPlano().equalsIgnoreCase("Premium"))) {
                res.status(403);
                return jsonResposta("erro", "Acesso negado: Exclusão de respostas oficiais é exclusiva dos planos Plus/Premium.");
            }

            avaliacaoDAO.atualizarResposta(id, null);
            res.status(200);
            return jsonResposta("msg", "Resposta oficial excluída com sucesso!");
        } catch (Exception e) {
            res.status(500);
            return jsonResposta("erro", e.getMessage());
        }
    }
}