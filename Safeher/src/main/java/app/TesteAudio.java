package app;

import service.ScoreService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TesteAudio {

    public static void main(String[] args) {

        try {

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "python",
                            "whisper/transcrever.py"
                    );

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream())
                    );

            BufferedReader errorReader =
                    new BufferedReader(
                            new InputStreamReader(process.getErrorStream())
                    );

            String linha;
            StringBuilder textoCompleto = new StringBuilder();

            while ((linha = reader.readLine()) != null) {

                textoCompleto.append(linha);
                textoCompleto.append(" ");
            }

            StringBuilder erros = new StringBuilder();

            while ((linha = errorReader.readLine()) != null) {

                erros.append(linha);
                erros.append("\n");
            }

            process.waitFor();

            if(erros.length() > 0) {

                System.out.println("ERRO NO WHISPER:");
                System.out.println(erros);

                return;
            }

            String texto = textoCompleto.toString();

            System.out.println("Texto transcrito:");
            System.out.println(texto);

            ScoreService scoreService = new ScoreService();

            int score = scoreService.calcularScore(texto);

            System.out.println("\nScore de risco: " + score);

            if(score == 5) {
                System.out.println("RISCO EXTREMO");
            }
            else if(score == 4) {
                System.out.println("RISCO ALTO");
            }
            else if(score == 3) {
                System.out.println("RISCO MODERADO");
            }
            else if(score == 2) {
                System.out.println("RISCO BAIXO");
            }
            else {
                System.out.println("RISCO MÍNIMO");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}