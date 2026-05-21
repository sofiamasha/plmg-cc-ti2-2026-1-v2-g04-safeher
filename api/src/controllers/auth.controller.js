const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { validationResult } = require('express-validator');
const { query } = require('../config/database');

/**
 * POST /api/auth/cadastro
 * Cria uma nova usuária e retorna JWT.
 */
async function cadastro(req, res, next) {
  try {
    // Validações de input
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const { nome, email, senha } = req.body;

    // Verifica se e-mail já está em uso
    const { rows: existe } = await query(
      'SELECT id FROM usuario WHERE email = $1',
      [email]
    );
    if (existe.length > 0) {
      return res.status(409).json({ erro: 'E-mail já cadastrado' });
    }

    // Hash da senha
    const senhaHash = await bcrypt.hash(senha, 10);

    // Insere usuária
    const { rows } = await query(
      `INSERT INTO usuario (nome, email, senha, data)
       VALUES ($1, $2, $3, CURRENT_DATE)
       RETURNING id, nome, email, data`,
      [nome, email, senhaHash]
    );

    const usuaria = rows[0];

    // Gera JWT
    const token = jwt.sign(
      { id: usuaria.id, email: usuaria.email, nome: usuaria.nome },
      process.env.JWT_SECRET,
      { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
    );

    return res.status(201).json({
      mensagem: 'Cadastro realizado com sucesso',
      token,
      usuario: { id: usuaria.id, nome: usuaria.nome, email: usuaria.email },
    });
  } catch (err) {
    next(err);
  }
}

/**
 * POST /api/auth/login
 * Autentica a usuária e retorna JWT.
 */
async function login(req, res, next) {
  try {
    const erros = validationResult(req);
    if (!erros.isEmpty()) {
      return res.status(422).json({ erros: erros.array() });
    }

    const { email, senha } = req.body;

    // Busca usuária pelo e-mail
    const { rows } = await query(
      'SELECT id, nome, email, senha FROM usuario WHERE email = $1',
      [email]
    );

    if (rows.length === 0) {
      return res.status(401).json({ erro: 'E-mail ou senha inválidos' });
    }

    const usuaria = rows[0];

    // Confere senha
    const senhaCorreta = await bcrypt.compare(senha, usuaria.senha);
    if (!senhaCorreta) {
      return res.status(401).json({ erro: 'E-mail ou senha inválidos' });
    }

    // Gera JWT
    const token = jwt.sign(
      { id: usuaria.id, email: usuaria.email, nome: usuaria.nome },
      process.env.JWT_SECRET,
      { expiresIn: process.env.JWT_EXPIRES_IN || '7d' }
    );

    return res.json({
      mensagem: 'Login realizado com sucesso',
      token,
      usuario: { id: usuaria.id, nome: usuaria.nome, email: usuaria.email },
    });
  } catch (err) {
    next(err);
  }
}

/**
 * GET /api/auth/perfil
 * Retorna os dados da usuária autenticada.
 * Útil para a extensão verificar se o token ainda é válido.
 */
async function perfil(req, res, next) {
  try {
    const { rows } = await query(
      'SELECT id, nome, email, data FROM usuario WHERE id = $1',
      [req.usuario.id]
    );

    if (rows.length === 0) {
      return res.status(404).json({ erro: 'Usuária não encontrada' });
    }

    res.json({ usuario: rows[0] });
  } catch (err) {
    next(err);
  }
}

module.exports = { cadastro, login, perfil };
