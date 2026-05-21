const { Router } = require('express');
const crypto = require('crypto');
const { body, validationResult } = require('express-validator');
const auth = require('../middlewares/auth.middleware');
const { query } = require('../config/database');

const router = Router();

// Por enquanto, qualquer usuaria autenticada pode solicitar uma chave
// (em producao, restrinja a um papel "admin").
router.use(auth);

/**
 * POST /api/admin/api-keys
 * Gera uma nova API key publica.
 * Body: { nomeApp, email, limiteDia? }
 */
router.post(
  '/api-keys',
  [
    body('nomeApp').trim().notEmpty().withMessage('Nome do app e obrigatorio'),
    body('email').isEmail().withMessage('E-mail invalido').normalizeEmail(),
    body('limiteDia').optional().isInt({ min: 1, max: 1000000 }),
  ],
  async (req, res, next) => {
    try {
      const erros = validationResult(req);
      if (!erros.isEmpty()) return res.status(422).json({ erros: erros.array() });

      const { nomeApp, email, limiteDia } = req.body;
      const chave = 'sh_live_' + crypto.randomBytes(18).toString('hex');

      const { rows } = await query(
        `INSERT INTO api_key (chave, nome_app, email, limite_dia)
         VALUES ($1, $2, $3, $4)
         RETURNING id, chave, nome_app, email, limite_dia, criada_em`,
        [chave, nomeApp, email, limiteDia || 1000]
      );
      res.status(201).json({ mensagem: 'API key gerada', apiKey: rows[0] });
    } catch (err) { next(err); }
  }
);

/**
 * GET /api/admin/api-keys
 * Lista todas as chaves emitidas (mascara a chave).
 */
router.get('/api-keys', async (req, res, next) => {
  try {
    const { rows } = await query(
      `SELECT id, nome_app, email, ativa, limite_dia, criada_em,
              CONCAT(SUBSTRING(chave, 1, 12), '...') AS chave_preview
         FROM api_key
        ORDER BY criada_em DESC`
    );
    res.json(rows);
  } catch (err) { next(err); }
});

/**
 * DELETE /api/admin/api-keys/:id
 * Desativa uma chave.
 */
router.delete('/api-keys/:id', async (req, res, next) => {
  try {
    const { rowCount } = await query(
      'UPDATE api_key SET ativa = FALSE WHERE id = $1',
      [req.params.id]
    );
    if (!rowCount) return res.status(404).json({ erro: 'Chave nao encontrada' });
    res.json({ mensagem: 'Chave desativada' });
  } catch (err) { next(err); }
});

module.exports = router;
