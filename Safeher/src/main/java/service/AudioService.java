package service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;

public class AudioService {

    // Chave e regiao lidas de variaveis de ambiente (AZURE_SPEECH_KEY, AZURE_SPEECH_REGION)
    private static final String SPEECH_KEY =
            System.getenv().getOrDefault("AZURE_SPEECH_KEY", "");

    private static final String SPEECH_REGION =
            System.getenv().getOrDefault("AZURE_SPEECH_REGION", "eastus");

    public static String transcreverAudio(String caminhoAudio)
            throws ExecutionException, InterruptedException {

        try {

            // converte para wav
            String caminhoWav =
                    AudioConverter.converterParaWav(caminhoAudio);

            // configuracao azure
            SpeechConfig speechConfig =
                    SpeechConfig.fromSubscription(
                            SPEECH_KEY,
                            SPEECH_REGION
                    );

            // idioma
            speechConfig.setSpeechRecognitionLanguage("pt-BR");

            // audio wav
            AudioConfig audioConfig =
                    AudioConfig.fromWavFileInput(caminhoWav);

            // reconhecedor
            SpeechRecognizer recognizer =
                    new SpeechRecognizer(
                            speechConfig,
                            audioConfig
                    );

            // processa audio
            SpeechRecognitionResult result =
                    recognizer.recognizeOnceAsync().get();

            // resposta
            if (result.getReason() ==
                    ResultReason.RecognizedSpeech) {

                return result.getText();

            } else if (result.getReason() ==
                    ResultReason.NoMatch) {

                return "Nenhuma fala reconhecida.";

            } else {

                return "Erro na transcricao.";
            }

        } catch (Exception e) {

            return "Erro ao processar audio: "
                    + e.getMessage();
        }
    }
}