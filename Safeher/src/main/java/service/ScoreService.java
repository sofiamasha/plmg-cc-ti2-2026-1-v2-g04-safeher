package service;

public class ScoreService {

    public int calcularScore(String texto) {

        texto = texto.toLowerCase();

        int score = 1;

        if (
                texto.contains("socorro") ||
                texto.contains("me ajuda") ||
                texto.contains("ajuda")
        ) {
            score++;
        }

        if (
                texto.contains("medo") ||
                texto.contains("perigo") ||
                texto.contains("ameaça")
        ) {
            score++;
        }

        if (
                texto.contains("não consigo sair") ||
                texto.contains("ele vai me bater") ||
                texto.contains("estou presa")
        ) {
            score++;
        }

        if (
                texto.contains("arma") ||
                texto.contains("vou morrer") ||
                texto.contains("mata")
        ) {
            score++;
        }

        if(score > 5) {
            score = 5;
        }

        return score;
    }
}