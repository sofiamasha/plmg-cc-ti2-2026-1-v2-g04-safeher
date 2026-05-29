const { validationResult } = require('express-validator');
const EmpresaModel = require('../models/empresa.model');
const AvaliacaoModel = require('../models/avaliacao.model');

/**
 * GET /api/empresas
 * Lista todas as empresas.
 */
async function listar(req, res, next) {
  try {
    const empresas = await EmpresaModel.listar();
    res.json(empresas);
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/empresas/buscar?nome=X
 * Busca empresa pelo nome (usado pela extensão).
 * Retorna score, quantidade de avaliações e motivos mais citados.
 */
async function buscarPorNome(req, res, next) {
  try {
    const { nome } = req.query;
    if (!nome || nome.trim() === '') {
      return res.status(400).json({ erro: 'Parâmetro "nome" é obrigatório' });
    }

    const empresas = await EmpresaModel.buscarPorNome(nome);

    if (empresas.length === 0) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }

    const resultados = await Promise.all(
      empresas.map(async (empresa) => {
        const metricas = await AvaliacaoModel.buscarMetricasPorEmpresa(empresa.id);
        const avaliacoes = await AvaliacaoModel.buscarRecentesPorEmpresa(empresa.id, 3);

        return {
          empresa: empresa.nome,
          score: parseFloat(metricas?.score) || 0,
          quantidadeAvaliacoes: parseInt(metricas?.quantidade_avaliacoes) || 0,
          avaliacoesRecentes: avaliacoes,
          detalhes: {
            id: empresa.id,
            cnpj: empresa.cnpj,
            endereco: empresa.endereco,
          },
        };
      })
    );

    res.json(resultados);
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/empresas/:id
 * Retorna empresa por ID.
 */
async function buscarPorId(req, res, next) {
  try {
    const empresa = await EmpresaModel.buscarPorId(req.params.id);
    if (!empresa) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }
    res.json(empresa);
  } catch (err) {
    next(err);
  }
}

/**
 * POST /api/empresas
 * Cadastra uma nova empresa.
 */
async function criar(req, res, next) {
  try {
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const empresa = await EmpresaModel.criar(req.body);

    res.status(201).json({
      mensagem: 'Empresa cadastrada com sucesso',
      empresa,
    });
  } catch (err) {
    next(err);
  }
}

/**
 * PUT /api/empresas/:id
 * Atualiza dados de uma empresa.
 */
async function atualizar(req, res, next) {
  try {
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const sucesso = await EmpresaModel.atualizar(req.params.id, req.body);

    if (!sucesso) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }

    res.json({ mensagem: 'Empresa atualizada com sucesso' });
  } catch (err) {
    next(err);
  }
}

/**
 * DELETE /api/empresas/:id
 * Remove uma empresa.
 */
async function remover(req, res, next) {
  try {
    const sucesso = await EmpresaModel.remover(req.params.id);
    if (!sucesso) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }
    res.json({ mensagem: 'Empresa removida com sucesso' });
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/empresas/:id/score
 * Retorna score geral e motivos da empresa — endpoint principal da extensão.
 */
async function score(req, res, next) {
  try {
    const { id } = req.params;

    const empresa = await EmpresaModel.buscarDadosBasicos(id);
    if (!empresa) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }

    const metricas = await AvaliacaoModel.buscarMetricasPorEmpresa(id);
    const motivos = await AvaliacaoModel.buscarMotivosPorEmpresa(id, 5);

    res.json({
      empresa: empresa.nome,
      score: parseFloat(metricas?.score) || 0,
      quantidadeAvaliacoes: parseInt(metricas?.quantidade_avaliacoes) || 0,
      motivos,
    });
  } catch (err) {
    next(err);
  }
}

/**
 * GET /empresa/score
 * Endpoint público da extensão de navegador (sem autenticação).
 * Retorna dados agregados da empresa baseado em avaliações e denúncias.
 */
async function obterScorePublico(req, res, next) {
  try {
    const { nome } = req.query;
    if (!nome || nome.trim() === '') {
      return res.status(400).json({ erro: 'Parâmetro "nome" é obrigatório' });
    }

    const { query } = require('../config/database');
    const empresas = await EmpresaModel.buscarPorNome(nome);

    if (empresas.length === 0) {
      return res.status(404).json({
        erro: 'Empresa sem dados no SafeHer',
        encontrada: false,
        nome: nome
      });
    }

    const empresa = empresas[0];
    const metricas = await AvaliacaoModel.buscarMetricasPorEmpresa(empresa.id);
    const { rows: denunciaRows } = await query(
      'SELECT COUNT(*) AS total FROM denuncia WHERE empresa_id = $1',
      [empresa.id]
    );

    const mediaAvaliacoes = parseFloat(metricas?.score) || 0;
    const totalAvaliacoes = parseInt(metricas?.quantidade_avaliacoes) || 0;
    const totalDenuncias = parseInt(denunciaRows[0]?.total) || 0;

    let score = 100;
    if (totalAvaliacoes > 0) {
      score = mediaAvaliacoes * 20;
    }
    // Cada denúncia registrada reduz o score de segurança em 10 pontos
    score = Math.max(0, Math.min(100, Math.round(score - (totalDenuncias * 10))));

    let status = 'Segura';
    let resumo = 'Excelente reputação. Ambiente de trabalho altamente seguro e recomendado.';

    if (score < 40) {
      status = 'Crítica';
      resumo = 'Alerta! Alto índice de denúncias ou avaliações extremamente negativas.';
    } else if (score < 70) {
      status = 'Atenção';
      resumo = 'Atenção. Existem registros de denúncias ou avaliações medianas.';
    }

    res.json({
      nome: empresa.nome,
      score,
      mediaAvaliacoes,
      totalAvaliacoes,
      denuncias: totalDenuncias,
      status,
      resumo,
      encontrada: true
    });
  } catch (err) {
    next(err);
  }
}

module.exports = {
  listar,
  buscarPorNome,
  buscarPorId,
  criar,
  atualizar,
  remover,
  score,
  obterScorePublico,
};

