package app;

import service.AudioService;

public class TesteAudio {

    public static void main(String[] args) {

        AudioService audioService = new AudioService();

        String texto =
                audioService.converterAudioParaTexto("audios/teste.wav");

        System.out.println("Texto reconhecido:");
        System.out.println(texto);
    }
}