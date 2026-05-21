const { validationResult } = require('express-validator');
const AvaliacaoModel = require('../models/avaliacao.model');
const EmpresaModel = require('../models/empresa.model');

/**
 * GET /api/avaliacoes
 * Lista todas as avaliações.
 */
async function listar(req, res, next) {
  try {
    const avaliacoes = await AvaliacaoModel.listar();
    res.json(avaliacoes);
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/avaliacoes/:id
 * Retorna avaliação por ID.
 */
async function buscarPorId(req, res, next) {
  try {
    const avaliacao = await AvaliacaoModel.buscarPorId(req.params.id);
    if (!avaliacao) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    res.json(avaliacao);
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/avaliacoes/empresa/:empresaId
 * Lista avaliações de uma empresa específica.
 */
async function listarPorEmpresa(req, res, next) {
  try {
    const avaliacoes = await AvaliacaoModel.listarPorEmpresa(req.params.empresaId);
    res.json(avaliacoes);
  } catch (err) {
    next(err);
  }
}

/**
 * POST /api/avaliacoes
 * Adiciona nova avaliação associada à usuária autenticada.
 * Body: { nota, comentario, empresaId, nomeEmpresa, tags[] }
 */
async function criar(req, res, next) {
  try {
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const dados = req.body;
    dados.usuarioId = req.usuario.id; // req.usuario é injetado pelo authMiddleware

    const avaliacao = await AvaliacaoModel.criar(dados);

    // Atualiza o índice (score médio) da empresa
    if (dados.empresaId) {
      await EmpresaModel.atualizarIndice(dados.empresaId);
    }

    res.status(201).json({
      mensagem: 'Avaliação registrada com sucesso',
      avaliacao,
    });
  } catch (err) {
    next(err);
  }
}

/**
 * PUT /api/avaliacoes/:id
 * Edita avaliação — apenas a autora pode editar.
 */
async function atualizar(req, res, next) {
  try {
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const { nota, comentario } = req.body;
    const { id } = req.params;
    const usuarioId = req.usuario.id;

    // Verifica se a avaliação pertence à usuária autenticada
    const existente = await AvaliacaoModel.buscarPorId(id);
    if (!existente) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    if (existente.usuario_id !== usuarioId) {
      return res.status(403).json({ erro: 'Você não tem permissão para editar esta avaliação' });
    }

    await AvaliacaoModel.atualizar(id, { nota, comentario });

    // Recalcula índice da empresa
    const empresaId = existente.empresa_id;
    if (empresaId) {
      await EmpresaModel.atualizarIndice(empresaId);
    }

    res.json({ mensagem: 'Avaliação atualizada com sucesso' });
  } catch (err) {
    next(err);
  }
}

/**
 * DELETE /api/avaliacoes/:id
 * Remove avaliação — apenas a autora pode remover.
 */
async function remover(req, res, next) {
  try {
    const { id } = req.params;
    const usuarioId = req.usuario.id;

    const existente = await AvaliacaoModel.buscarPorId(id);
    if (!existente) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    if (existente.usuario_id !== usuarioId) {
      return res.status(403).json({ erro: 'Você não tem permissão para remover esta avaliação' });
    }

    await AvaliacaoModel.remover(id);

    // Recalcula índice da empresa
    const empresaId = existente.empresa_id;
    if (empresaId) {
      await EmpresaModel.atualizarIndice(empresaId);
    }

    res.json({ mensagem: 'Avaliação removida com sucesso' });
  } catch (err) {
    next(err);
  }
}

module.exports = { listar, buscarPorId, listarPorEmpresa, criar, atualizar, remover };
