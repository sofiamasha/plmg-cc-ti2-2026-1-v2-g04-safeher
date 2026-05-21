package service;

import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class AudioService {

    // Chave lida da variavel de ambiente OPENAI_API_KEY.
    // NUNCA commite a chave no codigo fonte.
    // Defina antes de iniciar a aplicacao:
    //   PowerShell:  $env:OPENAI_API_KEY = "sk-..."
    //   Linux/Mac:   export OPENAI_API_KEY="sk-..."
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public static String transcreverAudio(String caminhoAudio) throws IOException {

        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IOException("OPENAI_API_KEY nao configurada. Defina a variavel de ambiente antes de iniciar a aplicacao.");
        }

        OkHttpClient client = new OkHttpClient();

        File audioFile = new File(caminhoAudio);

        RequestBody fileBody =
                RequestBody.create(
                        audioFile,
                        MediaType.parse("audio/wav")
                );

        MultipartBody requestBody =
                new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(
                                "file",
                                audioFile.getName(),
                                fileBody
                        )
                        .addFormDataPart("model", "whisper-1")
                        .build();

        Request request =
                new Request.Builder()
                        .url("https://api.openai.com/v1/audio/transcriptions")
                        .addHeader(
                                "Authorization",
                                "Bearer " + API_KEY
                        )
                        .post(requestBody)
                        .build();

        Response response =
                client.newCall(request).execute();

        String jsonResponse = response.body().string();
        com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.has("text") ? jsonObject.get("text").getAsString() : jsonResponse;
    }
}