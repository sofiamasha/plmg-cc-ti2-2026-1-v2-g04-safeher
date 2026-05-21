const { Router } = require('express');
const { body } = require('express-validator');
const { cadastro, login, perfil } = require('../controllers/auth.controller');
const auth = require('../middlewares/auth.middleware');

const router = Router();

/**
 * POST /api/auth/cadastro
 * Cria conta de usuária e retorna JWT.
 *
 * Body: { nome, email, senha }
 */
router.post(
  '/cadastro',
  [
    body('nome').trim().notEmpty().withMessage('Nome é obrigatório'),
    body('email').isEmail().withMessage('E-mail inválido').normalizeEmail(),
    body('senha').isLength({ min: 6 }).withMessage('Senha deve ter no mínimo 6 caracteres'),
  ],
  cadastro
);

/**
 * POST /api/auth/login
 * Autentica usuária e retorna JWT.
 *
 * Body: { email, senha }
 */
router.post(
  '/login',
  [
    body('email').isEmail().withMessage('E-mail inválido').normalizeEmail(),
    body('senha').notEmpty().withMessage('Senha é obrigatória'),
  ],
  login
);

/**
 * GET /api/auth/perfil
 * Retorna dados da usuária autenticada.
 * Requer token JWT no header Authorization.
 */
router.get('/perfil', auth, perfil);

module.exports = router;
