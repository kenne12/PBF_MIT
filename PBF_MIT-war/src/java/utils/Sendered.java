/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author USER
 */
public class Sendered {

    public static void send() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"from\": \"kenne\",\n    \"text\": \"Hello this is a bulk SMS FROM REST API\\r\\nStop au 36180\",\n    \"date\": \"2019-04-10 18:00:00\",\n    \"campaignName\": \"info\",\n    \"to\": [\n        \"+237673564186\",\n            ]\n}");
        Request request = new Request.Builder()
                .url("https://api.allmysms.com/sms/send/bulk")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.err.println(response.toString());
    }

}
