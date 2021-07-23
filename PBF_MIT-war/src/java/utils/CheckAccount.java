/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author USER
 */
public class CheckAccount {

    public static String detailCompteJsonOkHttp() throws IOException {
        //YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new Request.Builder().url("https://api.allmysms.com/account")
                .get()
                .addHeader("Authorization", "Basic YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==")
                .addHeader("cache-control", "no-cache")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        System.err.println(response.toString());
        return response.toString();
    }

    public static AllMySmsAccountCheck getAccountDetail(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AllMySmsAccountCheck account = objectMapper.readValue(jsonString, AllMySmsAccountCheck.class);
        return account;
    }

    public static Object getPingDetail(String jsonString, Object o) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object account = objectMapper.readValue(jsonString, o.getClass());
        return account;
    }

    //HTTP GET request
    public static String pingServer() throws Exception {
        String url = "https://api.allmysms.com/ping/";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //optional default is GET
        con.setRequestMethod("GET");
        //int responseCode = con.getResponseCode();
        //http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    //HTTP GET request
    public static String returnDetailCompteJson() throws Exception {

        //login : collegeco
        //password : 1234colco@
        //String url = "https://api.allmysms.com/account?subAccount=" + SessionMBean.getInstitution().getApiuser();
        String url = "https://api.allmysms.com/account";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //optional default is GET
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", "Basic YmVpbmluZm9zOjc4ZmVmY2E2MDI5YTkxYg==");
        con.setRequestProperty("cache-control", "no-cache");
        //con.setRequestProperty("subAccount", "collegeco");

        //add request header
        //con.setRequestProperty("User-Agent", USER__AGENT);
        //int responseCode = con.getResponseCode();
        //System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        //readStream(con.getInputStream());
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}
