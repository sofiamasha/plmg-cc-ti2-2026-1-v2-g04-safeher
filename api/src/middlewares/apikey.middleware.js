const { query } = require('../config/database');

/**
 * Middleware de validacao de API key publica.
 * Aceita a chave em:
 *   - Header  X-API-Key: <chave>
 *   - Query   ?api_key=<chave>
 *
 * Loga o uso em api_key_uso e aplica limite diario.
 */
async function apiKeyMiddleware(req, res, next) {
  try {
    const chave = req.header('X-API-Key') || req.query.api_key;
    if (!chave) {
      return res.status(401).json({ erro: 'API key obrigatoria (header X-API-Key ou ?api_key=)' });
    }

    const { rows } = await query(
      'SELECT id, nome_app, ativa, limite_dia FROM api_key WHERE chave = $1',
      [chave]
    );
    if (rows.length === 0) return res.status(401).json({ erro: 'API key invalida' });

    const apiKey = rows[0];
    if (!apiKey.ativa) return res.status(403).json({ erro: 'API key desativada' });

    // Limite diario
    const { rows: usoRows } = await query(
      `SELECT COUNT(*)::int AS total
         FROM api_key_uso
        WHERE api_key_id = $1
          AND data >= CURRENT_DATE`,
      [apiKey.id]
    );
    const usoHoje = usoRows[0]?.total || 0;
    if (usoHoje >= apiKey.limite_dia) {
      return res.status(429).json({
        erro: 'Limite diario atingido',
        limite: apiKey.limite_dia,
        usoHoje,
      });
    }

    // Registra uso (nao bloqueante para o cliente)
    query(
      'INSERT INTO api_key_uso (api_key_id, endpoint, ip) VALUES ($1, $2, $3)',
      [apiKey.id, req.originalUrl.split('?')[0], req.ip]
    ).catch(err => console.error('Falha ao logar uso de API key:', err.message));

    req.apiKey = apiKey;
    res.setHeader('X-RateLimit-Limit', apiKey.limite_dia);
    res.setHeader('X-RateLimit-Remaining', Math.max(0, apiKey.limite_dia - usoHoje - 1));
    next();
  } catch (err) {
    next(err);
  }
}

module.exports = apiKeyMiddleware;
