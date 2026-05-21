const { Router } = require('express');
const apiKey = require('../middlewares/apikey.middleware');
const EmpresaModel = require('../models/empresa.model');
const AvaliacaoModel = require('../models/avaliacao.model');

const router = Router();

// Todas as rotas publicas exigem API key
router.use(apiKey);

/**
 * GET /api/public/empresas/buscar?nome=X
 * Busca empresas por nome (dados agregados, sem PII).
 */
router.get('/empresas/buscar', async (req, res, next) => {
  try {
    const { nome } = req.query;
    if (!nome || !nome.trim()) {
      return res.status(400).json({ erro: 'Parametro "nome" e obrigatorio' });
    }
    const empresas = await EmpresaModel.buscarPorNome(nome);
    if (empresas.length === 0) return res.status(404).json({ erro: 'Empresa nao encontrada' });

    const resultados = await Promise.all(empresas.map(async (e) => {
      const m = await AvaliacaoModel.buscarMetricasPorEmpresa(e.id);
      return {
        id: e.id,
        nome: e.nome,
        score: parseFloat(m?.score) || 0,
        quantidadeAvaliacoes: parseInt(m?.quantidade_avaliacoes) || 0,
      };
    }));

    res.json({ parceiro: req.apiKey.nome_app, resultados });
  } catch (err) { next(err); }
});

/**
 * GET /api/public/empresas/:id/score
 * Score + motivos de uma empresa (formato pronto pra badge).
 */
router.get('/empresas/:id/score', async (req, res, next) => {
  try {
    const empresa = await EmpresaModel.buscarDadosBasicos(req.params.id);
    if (!empresa) return res.status(404).json({ erro: 'Empresa nao encontrada' });

    const metricas = await AvaliacaoModel.buscarMetricasPorEmpresa(empresa.id);
    const motivos = await AvaliacaoModel.buscarMotivosPorEmpresa(empresa.id, 5);

    res.json({
      empresa: empresa.nome,
      score: parseFloat(metricas?.score) || 0,
      quantidadeAvaliacoes: parseInt(metricas?.quantidade_avaliacoes) || 0,
      motivos,
      poweredBy: 'SafeHer',
    });
  } catch (err) { next(err); }
});

/**
 * GET /api/public/empresas/score-por-nome?nome=X
 * Atalho usado por sites de vagas: passa o nome da empresa direto e ja
 * recebe o score. Casa o primeiro match.
 */
router.get('/empresas/score-por-nome', async (req, res, next) => {
  try {
    const { nome } = req.query;
    if (!nome || !nome.trim()) {
      return res.status(400).json({ erro: 'Parametro "nome" e obrigatorio' });
    }
    const empresas = await EmpresaModel.buscarPorNome(nome);
    if (empresas.length === 0) {
      return res.json({
        empresa: nome,
        score: 0,
        quantidadeAvaliacoes: 0,
        motivos: [],
        encontrada: false,
        poweredBy: 'SafeHer',
      });
    }
    const empresa = empresas[0];
    const metricas = await AvaliacaoModel.buscarMetricasPorEmpresa(empresa.id);
    const motivos = await AvaliacaoModel.buscarMotivosPorEmpresa(empresa.id, 5);

    res.json({
      empresa: empresa.nome,
      score: parseFloat(metricas?.score) || 0,
      quantidadeAvaliacoes: parseInt(metricas?.quantidade_avaliacoes) || 0,
      motivos,
      encontrada: true,
      poweredBy: 'SafeHer',
    });
  } catch (err) { next(err); }
});

module.exports = router;
