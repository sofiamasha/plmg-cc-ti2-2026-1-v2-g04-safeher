package service;

import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class AudioService {

    private static final String API_KEY = "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";

    public static String transcreverAudio(String caminhoAudio) throws IOException {

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