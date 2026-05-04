package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import model.Avaliacao;
import model.Denuncia;
import model.Empresa;
import model.Usuario;
import service.AvaliacaoService;
import service.DenunciaService;
import service.EmpresaService;
import service.UsuarioService;

import java.time.LocalDate;

import static spark.Spark.*;

/**
 * Classe principal do sistema SafeHer.
 * Inicializa o servidor Spark e define todas as rotas da API REST.
 */
public class Aplicacao {

    /** Instâncias dos serviços */
    private static UsuarioService usuarioService = new UsuarioService();
    private static EmpresaService empresaService = new EmpresaService();
    private static AvaliacaoService avaliacaoService = new AvaliacaoService();
    private static DenunciaService denunciaService = new DenunciaService();

    /** Gson com suporte a LocalDate */
    private static Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class,
            (JsonSerializer<LocalDate>) (date, type, ctx) ->
                new JsonPrimitive(date.toString()))
        .registerTypeAdapter(LocalDate.class,
            (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                LocalDate.parse(json.getAsString()))
        .create();

    /**
     * Método principal que inicia o servidor.
     *
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        port(8080);

        // Serve os arquivos do front-end (HTML, CSS, JS, imagens)
        staticFiles.location("/front-end");

        // Permite requisições do front-end (CORS)
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });
        options("/*", (req, res) -> "OK");

        // =====================
        // CRUD - USUARIO
        // =====================

        /** Insere um novo usuário */
        post("/usuarios", (req, res) -> {
            res.type("application/json");
            try {
                Usuario usuario = gson.fromJson(req.body(), Usuario.class);
                usuarioService.insert(usuario);
                return gson.toJson("{\"msg\":\"Usuario inserido com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Atualiza um usuário pelo ID */
        put("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            try {
                Usuario usuario = gson.fromJson(req.body(), Usuario.class);
                usuario.setId(Integer.parseInt(req.params(":id")));
                usuarioService.update(usuario);
                return gson.toJson("{\"msg\":\"Usuario atualizado com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Remove um usuário pelo ID */
        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            try {
                usuarioService.remove(Integer.parseInt(req.params(":id")));
                return gson.toJson("{\"msg\":\"Usuario removido com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Busca um usuário pelo ID */
        get("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            try {
                Usuario usuario = usuarioService.get(Integer.parseInt(req.params(":id")));
                return gson.toJson(usuario);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Lista todos os usuários */
        get("/usuarios", (req, res) -> {
            res.type("application/json");
            try {
                return gson.toJson(usuarioService.listar());
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        // =====================
        // CRUD - EMPRESA
        // =====================

        /** Insere uma nova empresa */
        post("/empresas", (req, res) -> {
            res.type("application/json");
            try {
                Empresa empresa = gson.fromJson(req.body(), Empresa.class);
                empresaService.insert(empresa);
                return gson.toJson("{\"msg\":\"Empresa inserida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Atualiza uma empresa pelo ID */
        put("/empresas/:id", (req, res) -> {
            res.type("application/json");
            try {
                Empresa empresa = gson.fromJson(req.body(), Empresa.class);
                empresa.setId(Integer.parseInt(req.params(":id")));
                empresaService.update(empresa);
                return gson.toJson("{\"msg\":\"Empresa atualizada com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Remove uma empresa pelo ID */
        delete("/empresas/:id", (req, res) -> {
            res.type("application/json");
            try {
                empresaService.remove(Integer.parseInt(req.params(":id")));
                return gson.toJson("{\"msg\":\"Empresa removida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Busca uma empresa pelo ID */
        get("/empresas/:id", (req, res) -> {
            res.type("application/json");
            try {
                Empresa empresa = empresaService.get(Integer.parseInt(req.params(":id")));
                return gson.toJson(empresa);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Lista todas as empresas */
        get("/empresas", (req, res) -> {
            res.type("application/json");
            try {
                return gson.toJson(empresaService.listar());
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        // =====================
        // CRUD - AVALIACAO
        // =====================

        /** Insere uma nova avaliação */
        post("/avaliacoes", (req, res) -> {
            res.type("application/json");
            try {
                Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
                avaliacaoService.insert(avaliacao);
                return gson.toJson("{\"msg\":\"Avaliacao inserida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Atualiza uma avaliação pelo ID */
        put("/avaliacoes/:id", (req, res) -> {
            res.type("application/json");
            try {
                Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
                avaliacao.setId(Integer.parseInt(req.params(":id")));
                avaliacaoService.update(avaliacao);
                return gson.toJson("{\"msg\":\"Avaliacao atualizada com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Remove uma avaliação pelo ID */
        delete("/avaliacoes/:id", (req, res) -> {
            res.type("application/json");
            try {
                avaliacaoService.remove(Integer.parseInt(req.params(":id")));
                return gson.toJson("{\"msg\":\"Avaliacao removida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Busca uma avaliação pelo ID */
        get("/avaliacoes/:id", (req, res) -> {
            res.type("application/json");
            try {
                Avaliacao avaliacao = avaliacaoService.get(Integer.parseInt(req.params(":id")));
                return gson.toJson(avaliacao);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Lista todas as avaliações */
        get("/avaliacoes", (req, res) -> {
            res.type("application/json");
            try {
                return gson.toJson(avaliacaoService.listar());
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        // =====================
        // CRUD - DENUNCIA
        // =====================

        /** Insere uma nova denúncia */
        post("/denuncias", (req, res) -> {
            res.type("application/json");
            try {
                Denuncia denuncia = gson.fromJson(req.body(), Denuncia.class);
                denunciaService.insert(denuncia);
                return gson.toJson("{\"msg\":\"Denuncia inserida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Atualiza uma denúncia pelo ID */
        put("/denuncias/:id", (req, res) -> {
            res.type("application/json");
            try {
                Denuncia denuncia = gson.fromJson(req.body(), Denuncia.class);
                denuncia.setId(Integer.parseInt(req.params(":id")));
                denunciaService.update(denuncia);
                return gson.toJson("{\"msg\":\"Denuncia atualizada com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Remove uma denúncia pelo ID */
        delete("/denuncias/:id", (req, res) -> {
            res.type("application/json");
            try {
                denunciaService.remove(Integer.parseInt(req.params(":id")));
                return gson.toJson("{\"msg\":\"Denuncia removida com sucesso\"}");
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Busca uma denúncia pelo ID */
        get("/denuncias/:id", (req, res) -> {
            res.type("application/json");
            try {
                Denuncia denuncia = denunciaService.get(Integer.parseInt(req.params(":id")));
                return gson.toJson(denuncia);
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Lista todas as denúncias */
        get("/denuncias", (req, res) -> {
            res.type("application/json");
            try {
                return gson.toJson(denunciaService.listar());
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        /** Lista denúncias de um usuário específico */
        get("/denuncias/usuario/:usuarioId", (req, res) -> {
            res.type("application/json");
            try {
                return gson.toJson(denunciaService.listarPorUsuario(Integer.parseInt(req.params(":usuarioId"))));
            } catch (Exception e) {
                res.status(500);
                return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
            }
        });

        System.out.println("SafeHer API rodando na porta 8080");
    }
}
