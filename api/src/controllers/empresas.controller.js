const { validationResult } = require('express-validator');
const { query } = require('../config/database');

/**
 * GET /api/empresas
 * Lista todas as empresas.
 */
async function listar(req, res, next) {
  try {
    const { rows } = await query(
      'SELECT id, nome, indice, cnpj, cep, endereco, email, telefone FROM empresa ORDER BY nome'
    );
    res.json(rows);
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

    // Busca a empresa com busca parcial (ILIKE = case-insensitive)
    const { rows: empresas } = await query(
      `SELECT id, nome, indice, cnpj, cep, endereco, email, telefone
       FROM empresa
       WHERE nome ILIKE $1
       LIMIT 5`,
      [`%${nome.trim()}%`]
    );

    if (empresas.length === 0) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }

    // Para cada empresa encontrada, busca métricas de avaliações
    const resultados = await Promise.all(
      empresas.map(async (empresa) => {
        // Média de notas e contagem
        const { rows: metricas } = await query(
          `SELECT
             ROUND(AVG(nota)::numeric, 1) AS score,
             COUNT(*) AS quantidade_avaliacoes
           FROM avaliacao
           WHERE empresa_id = $1`,
          [empresa.id]
        );

        // Comentários recentes (para a extensão exibir)
        const { rows: avaliacoes } = await query(
          `SELECT comentario, nota, data_criacao
           FROM avaliacao
           WHERE empresa_id = $1
           ORDER BY data_criacao DESC
           LIMIT 3`,
          [empresa.id]
        );

        const { score, quantidade_avaliacoes } = metricas[0];

        return {
          empresa: empresa.nome,
          score: parseFloat(score) || 0,
          quantidadeAvaliacoes: parseInt(quantidade_avaliacoes),
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
    const { rows } = await query(
      'SELECT id, nome, indice, cnpj, cep, endereco, email, telefone FROM empresa WHERE id = $1',
      [req.params.id]
    );
    if (rows.length === 0) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }
    res.json(rows[0]);
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

    const { nome, cnpj, cep, endereco, email, telefone, senha } = req.body;

    const { rows } = await query(
      `INSERT INTO empresa (nome, indice, cnpj, cep, endereco, email, telefone, senha)
       VALUES ($1, 0, $2, $3, $4, $5, $6, $7)
       RETURNING id, nome, cnpj, email`,
      [nome, cnpj, cep, endereco, email, telefone, senha]
    );

    res.status(201).json({
      mensagem: 'Empresa cadastrada com sucesso',
      empresa: rows[0],
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

    const { nome, cnpj, cep, endereco, email, telefone } = req.body;
    const { id } = req.params;

    const { rowCount } = await query(
      `UPDATE empresa
       SET nome=$1, cnpj=$2, cep=$3, endereco=$4, email=$5, telefone=$6
       WHERE id=$7`,
      [nome, cnpj, cep, endereco, email, telefone, id]
    );

    if (rowCount === 0) {
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
    const { rowCount } = await query('DELETE FROM empresa WHERE id = $1', [req.params.id]);
    if (rowCount === 0) {
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

    // Dados da empresa
    const { rows: empresa } = await query(
      'SELECT id, nome FROM empresa WHERE id = $1',
      [id]
    );
    if (empresa.length === 0) {
      return res.status(404).json({ erro: 'Empresa não encontrada' });
    }

    // Métricas agregadas
    const { rows: metricas } = await query(
      `SELECT
         ROUND(AVG(nota)::numeric, 1) AS score,
         COUNT(*) AS quantidade_avaliacoes
       FROM avaliacao
       WHERE empresa_id = $1`,
      [id]
    );

    // Tags/motivos mais citados nas avaliações
    const { rows: motivos } = await query(
      `SELECT tag, COUNT(*) AS total
       FROM avaliacao_tag
       WHERE empresa_id = $1
       GROUP BY tag
       ORDER BY total DESC
       LIMIT 5`,
      [id]
    );

    res.json({
      empresa: empresa[0].nome,
      score: parseFloat(metricas[0].score) || 0,
      quantidadeAvaliacoes: parseInt(metricas[0].quantidade_avaliacoes),
      motivos: motivos.map(m => m.tag),
    });
  } catch (err) {
    next(err);
  }
}

module.exports = { listar, buscarPorNome, buscarPorId, criar, atualizar, remover, score };
