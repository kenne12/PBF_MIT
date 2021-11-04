/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author USER
 */
public class OrangeSmsSender {

    public static String send(String access_token, String message, String phoneNumer) {
        String senderAddress = "237695622901";
        //String requestBody = "{\n\t\"outboundSMSMessageRequest\": {\n\t\t\"address\": \"tel:+237" + phoneNumer + "\",\n\t\t\"senderAddress\": \"tel:+237695622901\",\n\t\t\"outboundSMSTextMessage\": {\n\t\t\t\"message\": \"" + message + "\"\n\t\t}\n\t}\n}";

        String requestBody = "{\r\n    \"outboundSMSMessageRequest\": {\r\n        \"address\":\"tel:+237" + phoneNumer + "\",\r\n        \"senderAddress\" : \"tel:+237" + senderAddress + "\",\r\n        \"outboundSMSTextMessage\" : {\r\n            \"message\" : \"" + message + "\"\r\n        }\r\n    }    \r\n}";

        System.err.println(requestBody);
        HttpURLConnection con = null;
        String result = null;
        try {
            //URL obj = new URL("https://api.orange.com/smsmessaging/v1/outbound/tel:+237673564186/requests");
            URL obj = new URL("https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B" + senderAddress + "/requests");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + access_token);
            //con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Accept", "*/*");

            //you can add any request body here if you want to post
            if (requestBody != null) {
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(requestBody);
                out.flush();
                out.close();
            }

            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            StringBuilder responseBuilder = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                responseBuilder.append(temp);
            }
            result = responseBuilder.toString();
            in.close();
            System.err.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static void googleSendJson(String access_token, OutboundSMSMessageRequest outboundSMSMessageRequest) {
        try {

            String senderAddress = "237695622901";
            //URL url = new URL("https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B" + senderAddress + "/requests");
            URL url = new URL("https://api.orange.com/smsmessaging/v1/outbound/tel:+" + senderAddress + "/requests");
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            // Create JSON request.
            //JSONObject jsonObj
            //        = new JSONObject().put("userId", 1).put("id", 1).put("title", "").put("body", "");
            String json = CheckAccount.fromObjectToJson(outboundSMSMessageRequest);

            String finalJson = "{\r\n   \"outboundSMSMessageRequest\" : \n" + json + "\r\n}";

            System.err.println("t\n" + finalJson);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(json);
            writer.close();

            int respCode = conn.getResponseCode(); // New items get NOT_FOUND on PUT
            //if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND) {
            //req.setAttribute("error", "");
            StringBuilder response = new StringBuilder();
            String line;

            // Read input data stream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.err.println(response.toString());
            //req.setAttribute("response", response.toString());
            //} else {
            //req.setAttribute("error", conn.getResponseCode() + " " + conn.getResponseMessage());
            //}

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static String auth() {
        String authorization_header = SessionMBean.getParametrage().getOrangeAuthHeader();
        String requestBody = "grant_type=client_credentials";
        HttpURLConnection con = null;
        String result = null;
        try {
            URL obj = new URL("https://api.orange.com/oauth/v3/token");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization", "Basic " + authorization_header);
            con.setRequestProperty("Accept", "application/json");

            //you can add any request body here if you want to post
            if (requestBody != null) {
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(requestBody);
                out.flush();
                out.close();
            }

            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            StringBuilder sb = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            result = sb.toString();
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static String getBalance(String access_token) {

        String reqbody = null;
        HttpURLConnection con = null;
        String result = null;
        try {
            URL obj = new URL("https://api.orange.com/sms/admin/v1/contracts");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + access_token);
            con.setRequestProperty("Accept", "application/json");
            //you can add any request body here if you want to post
            if (reqbody != null) {
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(reqbody);
                out.flush();
                out.close();
            }
            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            StringBuilder sb = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            result = sb.toString();
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static String send2(String access_token, String phoneNumber, String message) {
        String senderAddress = "673564186";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"outboundSMSMessageRequest\": {\r\n        \"address\":\"tel:+237" + phoneNumber + "\",\r\n        \"senderAddress\" : \"tel:+237" + senderAddress + "\",\r\n        \"outboundSMSTextMessage\" : {\r\n            \"message\" : \"" + message + "\"\r\n        }\r\n    }    \r\n}");
            Request request = new Request.Builder()
                    .url("https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B237" + senderAddress + "/requests")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + access_token)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            InputStream stream = response.body().byteStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(stream));

            String temp = null;
            StringBuilder responseBuiler = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                responseBuiler.append(temp);
            }

            response.body().close();
            System.err.println("\r\n" + responseBuiler.toString());
            return responseBuiler.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "00001";
        }
    }

}
