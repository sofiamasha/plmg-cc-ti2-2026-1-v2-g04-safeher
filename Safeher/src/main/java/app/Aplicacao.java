package app;

import service.AvaliacaoService;
import service.DenunciaService;
import service.EmpresaService;
import service.UsuarioService;

import static spark.Spark.*;

public class Aplicacao {

    private static UsuarioService usuarioService = new UsuarioService();
    private static EmpresaService empresaService = new EmpresaService();
    private static AvaliacaoService avaliacaoService = new AvaliacaoService();
    private static DenunciaService denunciaService = new DenunciaService();

    public static void main(String[] args) {
        port(8080);

        staticFiles.location("/front-end");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });
        options("/*", (req, res) -> "OK");

        post("/upload-audio", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new javax.servlet.MultipartConfigElement("/temp"));
            try (java.io.InputStream is = req.raw().getPart("audio").getInputStream()) {
                java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("audio", ".wav");
                java.nio.file.Files.copy(is, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                
                String texto = service.AudioService.transcreverAudio(tempFile.toAbsolutePath().toString());
                
                service.ScoreService scoreService = new service.ScoreService();
                int score = scoreService.calcularScore(texto);
                
                return "{\"texto\": " + new com.google.gson.Gson().toJson(texto) + ", \"score\": " + score + "}";
            } catch (Exception e) {
                res.status(500);
                return "{\"erro\":\"" + e.getMessage() + "\"}";
            }
        });

        post("/usuarios", (req, res) -> usuarioService.insert(req, res));
        put("/usuarios/:id", (req, res) -> usuarioService.update(req, res));
        delete("/usuarios/:id", (req, res) -> usuarioService.remove(req, res));
        get("/usuarios/:id", (req, res) -> usuarioService.get(req, res));
        get("/usuarios", (req, res) -> usuarioService.listar(req, res));

        post("/empresas", (req, res) -> empresaService.insert(req, res));
        put("/empresas/:id", (req, res) -> empresaService.update(req, res));
        delete("/empresas/:id", (req, res) -> empresaService.remove(req, res));
        get("/empresas/:id", (req, res) -> empresaService.get(req, res));
        get("/empresas", (req, res) -> empresaService.listar(req, res));
        get("/empresa/score", (req, res) -> empresaService.obterScorePublico(req, res));

        post("/avaliacoes", (req, res) -> avaliacaoService.insert(req, res));
        put("/avaliacoes/:id", (req, res) -> avaliacaoService.update(req, res));
        delete("/avaliacoes/:id", (req, res) -> avaliacaoService.remove(req, res));
        get("/avaliacoes/:id", (req, res) -> avaliacaoService.get(req, res));
        get("/avaliacoes", (req, res) -> avaliacaoService.listar(req, res));
        get("/avaliacoes/empresa/:empresaId", (req, res) -> avaliacaoService.listarPorEmpresa(req, res));

        post("/denuncias", (req, res) -> denunciaService.insert(req, res));
        put("/denuncias/:id", (req, res) -> denunciaService.update(req, res));
        delete("/denuncias/:id", (req, res) -> denunciaService.remove(req, res));
        get("/denuncias/painel-risco", (req, res) -> denunciaService.listarPainelRisco(req, res));
        get("/denuncias/:id", (req, res) -> denunciaService.get(req, res));
        get("/denuncias", (req, res) -> denunciaService.listar(req, res));
        get("/denuncias/usuario/:usuarioId", (req, res) -> denunciaService.listarPorUsuario(req, res));
        get("/denuncias/empresa/:empresaId", (req, res) -> denunciaService.listarPorEmpresa(req, res));

        System.out.println("SafeHer API rodando na porta 8080");
    }
}
