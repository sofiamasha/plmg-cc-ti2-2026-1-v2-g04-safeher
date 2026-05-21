-- Patch: alinha schema com o que a API Node espera.

-- Adiciona coluna data_criacao em avaliacao
ALTER TABLE avaliacao
    ADD COLUMN IF NOT EXISTS data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Tabela de tags/motivos das avaliacoes (usada pelo endpoint /score)
CREATE TABLE IF NOT EXISTS avaliacao_tag (
    id          SERIAL PRIMARY KEY,
    avaliacao_id INT REFERENCES avaliacao(id) ON DELETE CASCADE,
    empresa_id  INT REFERENCES empresa(id) ON DELETE CASCADE,
    tag         VARCHAR(80) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_avaliacao_tag_empresa ON avaliacao_tag(empresa_id);
