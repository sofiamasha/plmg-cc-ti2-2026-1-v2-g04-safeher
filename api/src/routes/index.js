const { Router } = require('express');
const authRoutes      = require('./auth.routes');
const empresasRoutes  = require('./empresas.routes');
const avaliacoesRoutes = require('./avaliacoes.routes');
const publicRoutes    = require('./public.routes');
const adminRoutes     = require('./admin.routes');

const router = Router();

/**
 * Agrega todas as rotas da API.
 *
 * Base: /api
 *   /api/auth/cadastro
 *   /api/auth/login
 *   /api/empresas/...
 *   /api/avaliacoes/...
 */
router.use('/auth',       authRoutes);
router.use('/empresas',   empresasRoutes);
router.use('/avaliacoes', avaliacoesRoutes);
router.use('/public',     publicRoutes);
router.use('/admin',      adminRoutes);

module.exports = router;
