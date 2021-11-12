/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.Security;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author USER
 */
public class ObmSms {

    public static String auth() {

        try {

            String apiData = "Aen3qWBDZ926B3OCM2JcXMgql1JUM4M9SE0XlsBqmIp4L6QMUY9zfeOWw9IgUeE26:j07fGw2MKSF892adOeQ9qK91U";
            String apiDataEncoded = Base64.getEncoder().encodeToString(apiData.getBytes());

            String dir = "C:\\Program Files\\Java\\jdk1.8.0_77\\jre\\lib\\security\\cacerts";

            System.setProperty("javax.net.ssl.trustStore", dir);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //TrustStore..
            char[] passphrase = "changeit".toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");
            //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(dir), passphrase); //path

            //TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509"); //instance
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();
            URL url = new URL("https://apisms.obmafrica.com/v1/token");

            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
            httpsCon.setSSLSocketFactory(sf);
            httpsCon.setRequestMethod("GET");
            httpsCon.setRequestProperty("Content-Type", "application/json");
            httpsCon.setRequestProperty("Authorization", "Basic " + apiDataEncoded);

            BufferedReader in = new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            //readStream(con.getInputStream());
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.err.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static String getBalance(String authToken) {
        try {

            String dir = "C:\\Program Files\\Java\\jdk1.8.0_77\\jre\\lib\\security\\cacerts";

            System.setProperty("javax.net.ssl.trustStore", dir);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //TrustStore..
            char[] passphrase = "changeit".toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");
            //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(dir), passphrase); //path

            //TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509"); //instance
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();
            URL url = new URL("https://apisms.obmafrica.com/v1/balance");

            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
            httpsCon.setSSLSocketFactory(sf);
            httpsCon.setRequestMethod("GET");
            httpsCon.setRequestProperty("Content-Type", "application/json");
            httpsCon.setRequestProperty("Authorization", "Bearer " + authToken);

            BufferedReader in = new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            //readStream(con.getInputStream());
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.err.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static String sendSms(String access_token, ObmSmsSendRequest sendRequest) {
        try {

            String dir = "C:\\Program Files\\Java\\jdk1.8.0_77\\jre\\lib\\security\\cacerts";

            System.setProperty("javax.net.ssl.trustStore", dir);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //TrustStore..
            char[] passphrase = "changeit".toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");
            //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(dir), passphrase); //path

            //TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509"); //instance
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();

            OkHttpClient client = new OkHttpClient();
            client.newBuilder()
                    .socketFactory(sf)
                    .build();

            String bodyJson = CheckAccount.fromObjectToJson(sendRequest);

            MediaType mediaType = MediaType.parse("application/json");
            mediaType.charset(Charset.forName("UTF-8"));
            RequestBody body = RequestBody.create(mediaType, bodyJson);
            Request request = new Request.Builder()
                    .url("https://apisms.obmafrica.com/v1/sendsms")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + access_token)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            System.err.println(response.toString());

            InputStream stream = response.body().byteStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(stream));

            String temp = null;
            StringBuilder responseBuiler = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                responseBuiler.append(temp);
            }

            response.body().close();

            System.err.println("\r\n" + responseBuiler.toString());

            String responseJson = "";
            if (response.code() == 200) {
                responseJson = responseBuiler.toString();
                responseJson = Utilitaires.removeCharInJson(responseJson, "{\"0\":");
                return responseJson;
            }
            return responseBuiler.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "00001";
        }
    }

    public static String sendSmsOld(String authToken, ObmSmsSendRequest sendRequest) {

        try {

            String dir = "C:\\Program Files\\Java\\jdk1.8.0_77\\jre\\lib\\security\\cacerts";

            System.setProperty("javax.net.ssl.trustStore", dir);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //TrustStore..
            char[] passphrase = "changeit".toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");
            //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(dir), passphrase); //path

            //TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509"); //instance
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();

            URL url = new URL("https://apisms.obmafrica.com/v1/sendsms");

            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
            httpsCon.setSSLSocketFactory(sf);
            httpsCon.setRequestMethod("POST");
            httpsCon.setRequestProperty("Content-Type", "application/json");
            httpsCon.setRequestProperty("Authorization", "Bearer " + authToken);
            httpsCon.setDoOutput(true);
            httpsCon.setDoInput(true);

            String json = CheckAccount.fromObjectToJson(sendRequest);
            if (json != null) {
                OutputStreamWriter writer = new OutputStreamWriter(httpsCon.getOutputStream());
                writer.write(json);
                writer.flush();
                writer.close();
            }
            httpsCon.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpsCon.getInputStream(), Charset.forName("UTF-8")));

            String inputLine;
            StringBuffer response = new StringBuffer();

            //readStream(con.getInputStream());
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.err.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "00001";
        }
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
