package service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.Future;

public class AudioService {

    private final String speechKey = "En74mVb2Krnz7Aat5vBABC8FZ4IEjOZqnZP1xngtwBeLTSx2ZhwoJQQJ99CEACZoyfiXJ3w3AAAEACOGGPoI";
    private final String speechRegion = "brazilsouth";

    public String converterAudioParaTexto(String caminhoAudio) {

        try {

            SpeechConfig speechConfig =
                    SpeechConfig.fromSubscription(speechKey, speechRegion);

            speechConfig.setSpeechRecognitionLanguage("pt-BR");

            AudioConfig audioConfig =
                    AudioConfig.fromWavFileInput(caminhoAudio);

            SpeechRecognizer recognizer =
                    new SpeechRecognizer(speechConfig, audioConfig);

            Future<SpeechRecognitionResult> task =
                    recognizer.recognizeOnceAsync();

            SpeechRecognitionResult result = task.get();

            if (result.getReason() == ResultReason.RecognizedSpeech) {

                return result.getText();

            } else {

                return "Não foi possível reconhecer o áudio.";

            }

        } catch (Exception e) {

            e.printStackTrace();
            return "Erro ao converter áudio.";

        }
    }
}