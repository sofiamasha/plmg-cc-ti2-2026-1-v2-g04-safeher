package app;

import service.AvaliacaoService;
import service.CandidaturaService;
import service.DenunciaService;
import service.EmpresaService;
import service.UsuarioService;
import service.VagaService;

import static spark.Spark.*;

public class Aplicacao {

    private static UsuarioService usuarioService = new UsuarioService();
    private static EmpresaService empresaService = new EmpresaService();
    private static AvaliacaoService avaliacaoService = new AvaliacaoService();
    private static DenunciaService denunciaService = new DenunciaService();
    private static VagaService vagaService = new VagaService();
    private static CandidaturaService candidaturaService = new CandidaturaService();

    public static void main(String[] args) {
        // Auto-geração dos ícones da extensão na inicialização do backend
        try {
            java.io.File sourceFile = new java.io.File("src/main/resources/front-end/resources/images/logo-escudo.png");
            if (!sourceFile.exists()) {
                sourceFile = new java.io.File("C:\\Users\\User\\OneDrive\\Área de Trabalho\\CC\\plmg-cc-ti2-2026-1-v2-g04-safeher\\Safeher\\src\\main\\resources\\front-end\\resources\\images\\logo-escudo.png");
            }
            if (sourceFile.exists()) {
                java.awt.image.BufferedImage originalImage = javax.imageio.ImageIO.read(sourceFile);
                int[] sizes = {16, 48, 128};
                String destDir = "../SafeHer-Extension/";
                java.io.File destDirFile = new java.io.File(destDir);
                if (!destDirFile.exists()) {
                    destDir = "C:\\Users\\User\\OneDrive\\Área de Trabalho\\CC\\plmg-cc-ti2-2026-1-v2-g04-safeher\\SafeHer-Extension\\";
                }
                for (int size : sizes) {
                    java.awt.image.BufferedImage resizedImage = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                    java.awt.Graphics2D g = resizedImage.createGraphics();
                    g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.drawImage(originalImage, 0, 0, size, size, null);
                    g.dispose();
                    
                    java.io.File outputFile = new java.io.File(destDir + "icon" + size + ".png");
                    javax.imageio.ImageIO.write(resizedImage, "png", outputFile);
                    System.out.println("[SafeHer] Ícone gerado: " + outputFile.getAbsolutePath());
                }
            } else {
                System.out.println("[SafeHer] Aviso: Logo-escudo não encontrado para gerar ícones.");
            }
        } catch (Exception e) {
            System.err.println("[SafeHer] Erro ao gerar ícones da extensão: " + e.getMessage());
        }

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
        get("/api/public/empresas/score-por-nome", (req, res) -> empresaService.obterScorePublicoPorNome(req, res));
        get("/api/public/empresas/buscar", (req, res) -> empresaService.buscarEmpresasPublico(req, res));
        get("/api/public/empresas/:id/score", (req, res) -> empresaService.obterScorePublicoPorId(req, res));
        post("/api/assinatura", (req, res) -> empresaService.atualizarAssinatura(req, res));

        post("/avaliacoes", (req, res) -> avaliacaoService.insert(req, res));
        put("/avaliacoes/:id", (req, res) -> avaliacaoService.update(req, res));
        delete("/avaliacoes/:id", (req, res) -> avaliacaoService.remove(req, res));
        get("/avaliacoes/:id", (req, res) -> avaliacaoService.get(req, res));
        get("/avaliacoes", (req, res) -> avaliacaoService.listar(req, res));
        get("/avaliacoes/empresa/:empresaId", (req, res) -> avaliacaoService.listarPorEmpresa(req, res));
        post("/avaliacoes/:id/resposta", (req, res) -> avaliacaoService.salvarResposta(req, res));
        delete("/avaliacoes/:id/resposta", (req, res) -> avaliacaoService.excluirResposta(req, res));

        post("/vagas", (req, res) -> vagaService.insert(req, res));
        get("/vagas", (req, res) -> vagaService.listar(req, res));
        get("/vagas/:id", (req, res) -> vagaService.get(req, res));
        get("/vagas/empresa/:empresaId", (req, res) -> vagaService.listarPorEmpresa(req, res));
        delete("/vagas/:id", (req, res) -> vagaService.remove(req, res));

        post("/candidaturas", (req, res) -> candidaturaService.insert(req, res));
        get("/candidaturas/vaga/:vagaId", (req, res) -> candidaturaService.listarPorVaga(req, res));

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
