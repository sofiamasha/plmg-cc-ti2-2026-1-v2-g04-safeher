const { query } = require('../config/database');

class AvaliacaoModel {
  static async listar() {
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
    return rows;
  }

  static async buscarPorId(id) {
    const { rows } = await query(
      `SELECT a.id, a.comentario, a.nota, a.usuario_id, a.empresa_id,
              a.nome_empresa, a.data_criacao,
              u.nome AS nome_usuario
       FROM avaliacao a
       LEFT JOIN usuario u ON u.id = a.usuario_id
       WHERE a.id = $1`,
      [id]
    );
    return rows[0] || null;
  }

  static async listarPorEmpresa(empresaId) {
    const { rows } = await query(
      `SELECT a.id, a.comentario, a.nota, a.usuario_id,
              a.nome_empresa, a.data_criacao
       FROM avaliacao a
       WHERE a.empresa_id = $1
       ORDER BY a.data_criacao DESC`,
      [empresaId]
    );
    return rows;
  }

  static async criar(dados) {
    const { comentario, nota, usuarioId, empresaId, nomeEmpresa } = dados;
    const { rows } = await query(
      `INSERT INTO avaliacao (comentario, nota, usuario_id, empresa_id, nome_empresa, data_criacao)
       VALUES ($1, $2, $3, $4, $5, CURRENT_TIMESTAMP)
       RETURNING id, comentario, nota, usuario_id, empresa_id, nome_empresa, data_criacao`,
      [comentario, nota, usuarioId, empresaId || null, nomeEmpresa]
    );
    return rows[0];
  }

  static async atualizar(id, dados) {
    const { nota, comentario } = dados;
    const { rowCount } = await query(
      'UPDATE avaliacao SET nota=$1, comentario=$2 WHERE id=$3',
      [nota, comentario, id]
    );
    return rowCount > 0;
  }

  static async remover(id) {
    const { rowCount } = await query('DELETE FROM avaliacao WHERE id = $1', [id]);
    return rowCount > 0;
  }

  // Métricas agregadas usadas no Score
  static async buscarMetricasPorEmpresa(empresaId) {
    const { rows } = await query(
      `SELECT
         ROUND(AVG(nota)::numeric, 1) AS score,
         COUNT(*) AS quantidade_avaliacoes
       FROM avaliacao
       WHERE empresa_id = $1`,
      [empresaId]
    );
    return rows[0];
  }

  static async buscarRecentesPorEmpresa(empresaId, limite = 3) {
    const { rows } = await query(
      `SELECT comentario, nota, data_criacao
       FROM avaliacao
       WHERE empresa_id = $1
       ORDER BY data_criacao DESC
       LIMIT $2`,
      [empresaId, limite]
    );
    return rows;
  }

  static async buscarMotivosPorEmpresa(empresaId, limite = 5) {
    const { rows } = await query(
      `SELECT tag, COUNT(*) AS total
       FROM avaliacao_tag
       WHERE empresa_id = $1
       GROUP BY tag
       ORDER BY total DESC
       LIMIT $2`,
      [empresaId, limite]
    );
    return rows.map(m => m.tag);
  }
}

module.exports = AvaliacaoModel;
