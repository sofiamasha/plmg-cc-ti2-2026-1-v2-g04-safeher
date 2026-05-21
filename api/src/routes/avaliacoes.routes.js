const { Router } = require('express');
const { body } = require('express-validator');
const auth = require('../middlewares/auth.middleware');
const {
  listar,
  buscarPorId,
  listarPorEmpresa,
  criar,
  atualizar,
  remover,
} = require('../controllers/avaliacoes.controller');

const router = Router();

// Todas as rotas de avaliação exigem autenticação
router.use(auth);

/**
 * GET /api/avaliacoes
 * Lista todas as avaliações.
 */
router.get('/', listar);

/**
 * GET /api/avaliacoes/empresa/:empresaId
 * Lista avaliações de uma empresa específica.
 * ⚠️  Deve ficar ANTES de /:id.
 */
router.get('/empresa/:empresaId', listarPorEmpresa);

/**
 * GET /api/avaliacoes/:id
 * Retorna avaliação por ID.
 */
router.get('/:id', buscarPorId);

/**
 * POST /api/avaliacoes
 * Adiciona avaliação.
 *
 * Body: { nota, comentario, empresaId?, nomeEmpresa }
 */
router.post(
  '/',
  [
    body('nota')
      .isInt({ min: 1, max: 5 })
      .withMessage('Nota deve ser um número inteiro entre 1 e 5'),
    body('comentario')
      .trim()
      .notEmpty()
      .withMessage('Comentário é obrigatório')
      .isLength({ max: 1000 })
      .withMessage('Comentário não pode ultrapassar 1000 caracteres'),
    body('nomeEmpresa').trim().notEmpty().withMessage('Nome da empresa é obrigatório'),
    body('empresaId').optional().isInt().withMessage('empresaId deve ser um número inteiro'),
  ],
  criar
);

/**
 * PUT /api/avaliacoes/:id
 * Edita avaliação — apenas a autora pode editar.
 *
 * Body: { nota?, comentario? }
 */
router.put(
  '/:id',
  [
    body('nota')
      .optional()
      .isInt({ min: 1, max: 5 })
      .withMessage('Nota deve ser um número inteiro entre 1 e 5'),
    body('comentario')
      .optional()
      .trim()
      .isLength({ max: 1000 })
      .withMessage('Comentário não pode ultrapassar 1000 caracteres'),
  ],
  atualizar
);

/**
 * DELETE /api/avaliacoes/:id
 * Remove avaliação — apenas a autora pode remover.
 */
router.delete('/:id', remover);

module.exports = router;
