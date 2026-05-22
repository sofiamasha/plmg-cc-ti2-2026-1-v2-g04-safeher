package service;

import java.io.File;
import java.io.IOException;

public class AudioConverter {

    public static String converterParaWav(String caminhoOriginal)
            throws IOException, InterruptedException {

        File arquivoOriginal = new File(caminhoOriginal);

        // verifica existencia
        if (!arquivoOriginal.exists()) {

            throw new IOException(
                    "Arquivo original nao encontrado."
            );
        }

        // nome wav
        String caminhoWav =
                caminhoOriginal + ".wav";

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        "ffmpeg",
                        "-i",
                        caminhoOriginal,
                        "-ar", "16000",
                        "-ac", "1",
                        "-c:a", "pcm_s16le",
                        caminhoWav
                );

        processBuilder.redirectErrorStream(true);

        Process process =
                processBuilder.start();

        int exitCode =
                process.waitFor();

        if (exitCode != 0) {

            throw new IOException(
                    "FFmpeg falhou na conversao."
            );
        }

        File wavFile =
                new File(caminhoWav);

        if (!wavFile.exists()) {

            throw new IOException(
                    "Arquivo WAV nao criado."
            );
        }

        return caminhoWav;
    }
}