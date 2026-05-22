-- Adiciona coluna score (gravidade detectada pela IA) na tabela Denuncia
-- Score: 1 (Baixo) a 5 (Critico)
ALTER TABLE Denuncia ADD COLUMN IF NOT EXISTS score INT DEFAULT 1;
