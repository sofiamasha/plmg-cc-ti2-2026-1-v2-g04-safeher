/**
 * Middleware global de tratamento de erros.
 * Captura qualquer erro não tratado pelos controllers e retorna JSON padronizado.
 */
function errorMiddleware(err, req, res, next) {
  console.error(`[ERRO] ${req.method} ${req.path}:`, err.message);

  // Erros de validação do express-validator são tratados nos controllers.
  // Aqui tratamos erros inesperados.

  const status = err.status || err.statusCode || 500;
  const mensagem = process.env.NODE_ENV === 'production'
    ? 'Erro interno no servidor'
    : err.message || 'Erro interno no servidor';

  res.status(status).json({ erro: mensagem });
}

module.exports = errorMiddleware;
