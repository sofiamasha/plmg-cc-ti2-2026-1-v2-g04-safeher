const { Router } = require('express');
const { body, param } = require('express-validator');
const auth = require('../middlewares/auth.middleware');
const {
  listar,
  buscarPorNome,
  buscarPorId,
  criar,
  atualizar,
  remover,
  score,
} = require('../controllers/empresas.controller');

const router = Router();

// Todas as rotas de empresa exigem autenticação
router.use(auth);

/**
 * GET /api/empresas
 * Lista todas as empresas.
 */
router.get('/', listar);

/**
 * GET /api/empresas/buscar?nome=X
 * Busca empresa pelo nome — endpoint principal usado pela extensão.
 * ⚠️  Deve ficar ANTES de /:id para não ser capturado como parâmetro.
 */
router.get('/buscar', buscarPorNome);

/**
 * GET /api/empresas/:id
 * Retorna empresa por ID.
 */
router.get('/:id', buscarPorId);

/**
 * GET /api/empresas/:id/score
 * Retorna score geral + motivos da empresa (formato da extensão).
 */
router.get('/:id/score', score);

/**
 * POST /api/empresas
 * Cadastra uma nova empresa.
 *
 * Body: { nome, cnpj?, cep?, endereco?, email?, telefone?, senha? }
 */
router.post(
  '/',
  [
    body('nome').trim().notEmpty().withMessage('Nome da empresa é obrigatório'),
    body('email').optional().isEmail().withMessage('E-mail inválido'),
    body('cnpj').optional().trim(),
  ],
  criar
);

/**
 * PUT /api/empresas/:id
 * Atualiza dados de uma empresa.
 */
router.put(
  '/:id',
  [
    body('nome').optional().trim().notEmpty().withMessage('Nome não pode ser vazio'),
    body('email').optional().isEmail().withMessage('E-mail inválido'),
  ],
  atualizar
);

/**
 * DELETE /api/empresas/:id
 * Remove uma empresa.
 */
router.delete('/:id', remover);

module.exports = router;
