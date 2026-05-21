const { Pool } = require('pg');

/**
 * Pool de conexões com o PostgreSQL.
 * Reutiliza o mesmo banco do backend Java (safeher).
 * Configuração via variáveis de ambiente (.env).
 */
const pool = new Pool({
  host:     process.env.DB_HOST     || 'localhost',
  port:     parseInt(process.env.DB_PORT || '5432'),
  database: process.env.DB_NAME     || 'safeher',
  user:     process.env.DB_USER     || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
  // Mantém até 10 conexões abertas simultaneamente
  max: 10,
  idleTimeoutMillis: 30000,
  connectionTimeoutMillis: 2000,
});

// Testa a conexão ao iniciar
pool.connect((err, client, release) => {
  if (err) {
    console.error('❌ Erro ao conectar ao banco de dados:', err.message);
    return;
  }
  release();
  console.log('✅ Conexão com PostgreSQL estabelecida');
});

/**
 * Executa uma query SQL com parâmetros opcionais.
 * @param {string} text   - Query SQL (ex: 'SELECT * FROM usuario WHERE id = $1')
 * @param {any[]}  params - Parâmetros da query
 */
const query = (text, params) => pool.query(text, params);

module.exports = { pool, query };
