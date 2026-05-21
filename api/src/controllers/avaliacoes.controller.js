const { validationResult } = require('express-validator');
const { query } = require('../config/database');

/**
 * GET /api/avaliacoes
 * Lista todas as avaliações.
 */
async function listar(req, res, next) {
  try {
    const { rows } = await query(
      `SELECT a.id, a.comentario, a.nota, a.usuario_id, a.empresa_id,
              a.nome_empresa, a.data_criacao,
              u.nome AS nome_usuario,
              e.nome AS nome_empresa_fk
       FROM avaliacao a
       LEFT JOIN usuario u ON u.id = a.usuario_id
       LEFT JOIN empresa e ON e.id = a.empresa_id
       ORDER BY a.data_criacao DESC`
    );
    res.json(rows);
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
    const { rows } = await query(
      `SELECT a.id, a.comentario, a.nota, a.usuario_id, a.empresa_id,
              a.nome_empresa, a.data_criacao,
              u.nome AS nome_usuario
       FROM avaliacao a
       LEFT JOIN usuario u ON u.id = a.usuario_id
       WHERE a.id = $1`,
      [req.params.id]
    );
    if (rows.length === 0) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    res.json(rows[0]);
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
    const { empresaId } = req.params;
    const { rows } = await query(
      `SELECT a.id, a.comentario, a.nota, a.usuario_id,
              a.nome_empresa, a.data_criacao
       FROM avaliacao a
       WHERE a.empresa_id = $1
       ORDER BY a.data_criacao DESC`,
      [empresaId]
    );
    res.json(rows);
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

    const { nota, comentario, empresaId, nomeEmpresa } = req.body;
    // req.usuario é injetado pelo authMiddleware
    const usuarioId = req.usuario.id;

    const { rows } = await query(
      `INSERT INTO avaliacao (comentario, nota, usuario_id, empresa_id, nome_empresa, data_criacao)
       VALUES ($1, $2, $3, $4, $5, CURRENT_TIMESTAMP)
       RETURNING id, comentario, nota, usuario_id, empresa_id, nome_empresa, data_criacao`,
      [comentario, nota, usuarioId, empresaId || null, nomeEmpresa]
    );

    // Atualiza o índice (score médio) da empresa
    if (empresaId) {
      await query(
        `UPDATE empresa
         SET indice = (
           SELECT ROUND(AVG(nota)::numeric, 2)
           FROM avaliacao
           WHERE empresa_id = $1
         )
         WHERE id = $1`,
        [empresaId]
      );
    }

    res.status(201).json({
      mensagem: 'Avaliação registrada com sucesso',
      avaliacao: rows[0],
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
    const { rows: existente } = await query(
      'SELECT id, usuario_id, empresa_id FROM avaliacao WHERE id = $1',
      [id]
    );
    if (existente.length === 0) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    if (existente[0].usuario_id !== usuarioId) {
      return res.status(403).json({ erro: 'Você não tem permissão para editar esta avaliação' });
    }

    await query(
      'UPDATE avaliacao SET nota=$1, comentario=$2 WHERE id=$3',
      [nota, comentario, id]
    );

    // Recalcula índice da empresa
    const empresaId = existente[0].empresa_id;
    if (empresaId) {
      await query(
        `UPDATE empresa
         SET indice = (SELECT ROUND(AVG(nota)::numeric, 2) FROM avaliacao WHERE empresa_id = $1)
         WHERE id = $1`,
        [empresaId]
      );
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

    const { rows: existente } = await query(
      'SELECT id, usuario_id, empresa_id FROM avaliacao WHERE id = $1',
      [id]
    );
    if (existente.length === 0) {
      return res.status(404).json({ erro: 'Avaliação não encontrada' });
    }
    if (existente[0].usuario_id !== usuarioId) {
      return res.status(403).json({ erro: 'Você não tem permissão para remover esta avaliação' });
    }

    await query('DELETE FROM avaliacao WHERE id = $1', [id]);

    // Recalcula índice da empresa
    const empresaId = existente[0].empresa_id;
    if (empresaId) {
      await query(
        `UPDATE empresa
         SET indice = COALESCE(
           (SELECT ROUND(AVG(nota)::numeric, 2) FROM avaliacao WHERE empresa_id = $1),
           0
         )
         WHERE id = $1`,
        [empresaId]
      );
    }

    res.json({ mensagem: 'Avaliação removida com sucesso' });
  } catch (err) {
    next(err);
  }
}

module.exports = { listar, buscarPorId, listarPorEmpresa, criar, atualizar, remover };
