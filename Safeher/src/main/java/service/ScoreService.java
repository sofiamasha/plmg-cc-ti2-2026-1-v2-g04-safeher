package service;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreService {

    // Termos por nivel de gravidade. Um termo terminado em "*" e tratado como
    // radical (ex.: "ameac*" cobre ameaca, ameacou, ameacando...).
    private static final List<String> NIVEL_BAIXO = List.of(
            "me ajuda", "sozinha", "nervosa", "assustada"
    );
    private static final List<String> NIVEL_MEDIO = List.of(
            "medo", "perigo", "ameac*", "assedi*", "persegu*",
            "nao me deixa", "intimid*"
    );
    private static final List<String> NIVEL_ALTO = List.of(
            "socorro", "estou presa", "nao consigo sair", "trancada",
            "me bateu", "apanhando", "agred*", "agress*", "violenc*", "sequestr*"
    );
    private static final List<String> NIVEL_CRITICO = List.of(
            "arma", "faca", "vou morrer", "vai me matar", "me matar",
            "ele vai me bater", "esfaque*", "atirar", "atirou", "espanc*", "sangue"
    );

    private static final List<String> NEGACOES = List.of(
            "nao", "nunca", "jamais", "sem", "nenhum", "nenhuma"
    );

    public int calcularScore(String texto) {
        if (texto == null || texto.isBlank()) {
            return 1;
        }
        String normalizado = normalizar(texto);
        int nivel = 0;
        if (contemAlgum(normalizado, NIVEL_BAIXO))   nivel = Math.max(nivel, 1);
        if (contemAlgum(normalizado, NIVEL_MEDIO))   nivel = Math.max(nivel, 2);
        if (contemAlgum(normalizado, NIVEL_ALTO))    nivel = Math.max(nivel, 3);
        if (contemAlgum(normalizado, NIVEL_CRITICO)) nivel = Math.max(nivel, 4);
        // nivel de gravidade (0..4) mapeado para a escala final de risco (1..5)
        return nivel + 1;
    }

    // Remove acentos e coloca em minusculo para comparacao consistente.
    private String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return semAcento.toLowerCase();
    }

    private boolean contemAlgum(String texto, List<String> termos) {
        for (String termo : termos) {
            if (contemTermoSemNegacao(texto, termo)) {
                return true;
            }
        }
        return false;
    }

    // Procura o termo respeitando limites de palavra e ignora ocorrencias
    // negadas (ex.: "nao tenho medo" nao conta como "medo").
    private boolean contemTermoSemNegacao(String texto, String termo) {
        String regex;
        if (termo.endsWith("*")) {
            String radical = termo.substring(0, termo.length() - 1);
            regex = "\\b" + Pattern.quote(radical) + "\\w*";
        } else {
            regex = "\\b" + Pattern.quote(termo) + "\\b";
        }
        Matcher m = Pattern.compile(regex).matcher(texto);
        while (m.find()) {
            if (!terminaComNegacao(texto.substring(0, m.start()))) {
                return true; // ocorrencia nao negada
            }
        }
        return false;
    }

    // Olha as ultimas 3 palavras antes do termo em busca de negacao.
    private boolean terminaComNegacao(String trecho) {
        String[] palavras = trecho.trim().split("\\s+");
        int janela = Math.min(3, palavras.length);
        for (int i = palavras.length - janela; i < palavras.length; i++) {
            if (i >= 0 && NEGACOES.contains(palavras[i])) {
                return true;
            }
        }
        return false;
    }
}