const { query } = require('../config/database');

class EmpresaModel {
  static async listar() {
    const { rows } = await query(
      'SELECT id, nome, indice, cnpj, cep, endereco, email, telefone FROM empresa ORDER BY nome'
    );
    return rows;
  }

  static async buscarPorNome(nome) {
    const { rows } = await query(
      `SELECT id, nome, indice, cnpj, cep, endereco, email, telefone
       FROM empresa
       WHERE nome ILIKE $1
       LIMIT 5`,
      [`%${nome.trim()}%`]
    );
    return rows;
  }

  static async buscarPorId(id) {
    const { rows } = await query(
      'SELECT id, nome, indice, cnpj, cep, endereco, email, telefone FROM empresa WHERE id = $1',
      [id]
    );
    return rows[0] || null;
  }

  static async criar(dados) {
    const { nome, cnpj, cep, endereco, email, telefone, senha } = dados;
    const { rows } = await query(
      `INSERT INTO empresa (nome, indice, cnpj, cep, endereco, email, telefone, senha)
       VALUES ($1, 0, $2, $3, $4, $5, $6, $7)
       RETURNING id, nome, cnpj, email`,
      [nome, cnpj, cep, endereco, email, telefone, senha]
    );
    return rows[0];
  }

  static async atualizar(id, dados) {
    const { nome, cnpj, cep, endereco, email, telefone } = dados;
    const { rowCount } = await query(
      `UPDATE empresa
       SET nome=$1, cnpj=$2, cep=$3, endereco=$4, email=$5, telefone=$6
       WHERE id=$7`,
      [nome, cnpj, cep, endereco, email, telefone, id]
    );
    return rowCount > 0;
  }

  static async remover(id) {
    const { rowCount } = await query('DELETE FROM empresa WHERE id = $1', [id]);
    return rowCount > 0;
  }

  static async buscarDadosBasicos(id) {
    const { rows } = await query('SELECT id, nome FROM empresa WHERE id = $1', [id]);
    return rows[0] || null;
  }

  static async atualizarIndice(id) {
    const { rowCount } = await query(
      `UPDATE empresa
       SET indice = COALESCE(
         (SELECT ROUND(AVG(nota)::numeric, 2) FROM avaliacao WHERE empresa_id = $1),
         0
       )
       WHERE id = $1`,
      [id]
    );
    return rowCount > 0;
  }
}

module.exports = EmpresaModel;
