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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 *
 * @author USER
 */
public class CheckAccount {

    public static AllMySmsAccountCheck getAccountDetail(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AllMySmsAccountCheck account = objectMapper.readValue(jsonString, AllMySmsAccountCheck.class);
        return account;
    }

    public static Object fromJsonToObject(String jsonString, Object o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object object = objectMapper.readValue(jsonString, o.getClass());
            return object;
        } catch (IOException ex) {
            return null;
        }
    }

    public static String fromObjectToJson(Object o) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    //HTTP GET request
    public static String pingServer() throws Exception {
        //String url = "https://api.allmysms.com/ping/";
        //String url = "http://51.68.84.221:8096/api/ping";
        String url = SessionMBean.getParametrage().getAllmysmsApiUrl() + "/api/ping";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.err.println(response.toString());
        return response.toString();
    }

    //HTTP GET request
    public static String returnDetailCompteJson(String apiAuthKey) throws Exception {

        //String url = "http://51.68.84.221:8096/api/accountDetails?apiAuthKey=" + apiAuthKey;
        String url = SessionMBean.getParametrage().getAllmysmsApiUrl() + "/api/accountDetails?apiAuthKey=" + apiAuthKey;
        //String url = "https://api.allmysms.com/account";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

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
