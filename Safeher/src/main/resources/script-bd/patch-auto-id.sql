-- Patch: adiciona auto-incremento via sequences nas tabelas existentes.
-- Mantém compatibilidade com o codigo Java legado (que ainda fornece id manualmente
-- via MAX+1; PostgreSQL aceita override e o nextval so e usado quando a API Node
-- omite o id no INSERT).

-- Usuario
CREATE SEQUENCE IF NOT EXISTS usuario_id_seq;
SELECT setval('usuario_id_seq', COALESCE((SELECT MAX(id) FROM usuario), 0));
ALTER TABLE usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq');
ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;

-- Empresa
CREATE SEQUENCE IF NOT EXISTS empresa_id_seq;
SELECT setval('empresa_id_seq', COALESCE((SELECT MAX(id) FROM empresa), 0));
ALTER TABLE empresa ALTER COLUMN id SET DEFAULT nextval('empresa_id_seq');
ALTER SEQUENCE empresa_id_seq OWNED BY empresa.id;

-- Avaliacao
CREATE SEQUENCE IF NOT EXISTS avaliacao_id_seq;
SELECT setval('avaliacao_id_seq', COALESCE((SELECT MAX(id) FROM avaliacao), 0));
ALTER TABLE avaliacao ALTER COLUMN id SET DEFAULT nextval('avaliacao_id_seq');
ALTER SEQUENCE avaliacao_id_seq OWNED BY avaliacao.id;

-- Denuncia
CREATE SEQUENCE IF NOT EXISTS denuncia_id_seq;
SELECT setval('denuncia_id_seq', COALESCE((SELECT MAX(id) FROM denuncia), 0));
ALTER TABLE denuncia ALTER COLUMN id SET DEFAULT nextval('denuncia_id_seq');
ALTER SEQUENCE denuncia_id_seq OWNED BY denuncia.id;
