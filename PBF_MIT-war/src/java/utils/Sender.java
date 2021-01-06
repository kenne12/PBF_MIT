package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Sender {

    String username;
    String password;
    String message;
    String type;
    String dlr;
    String destination;
    String source;
    String server;
    int port;

    public Sender(String server, int port, String username, String password, String message, String dlr, String type, String destination, String source) {
        this.username = username;
        this.password = password;
        this.message = message;
        this.dlr = dlr;
        this.type = type;
        this.destination = destination;
        this.source = source;
        this.server = server;
        this.port = port;
    }

    public void submitMessage() {
        try {
            URL sendUrl = new URL("http://" + this.server + ":" + this.port + "/bulksms/bulksms");
            HttpURLConnection httpConnection = (HttpURLConnection) sendUrl.openConnection();

            httpConnection.setRequestMethod("POST");

            httpConnection.setDoInput(true);

            httpConnection.setDoOutput(true);

            httpConnection.setUseCaches(false);

            DataOutputStream dataStreamToServer = new DataOutputStream(httpConnection.getOutputStream());
            dataStreamToServer.writeBytes("username="
                    + URLEncoder.encode(this.username, "UTF-8")
                    + "&password="
                    + URLEncoder.encode(this.password, "UTF-8")
                    + "&type="
                    + URLEncoder.encode(this.type, "UTF-8")
                    + "&dlr="
                    + URLEncoder.encode(this.dlr, "UTF-8")
                    + "&destination="
                    + URLEncoder.encode(this.destination, "UTF-8")
                    + "&source="
                    + URLEncoder.encode(this.source, "UTF-8")
                    + "&message="
                    + URLEncoder.encode(this.message, "UTF-8"));

            dataStreamToServer.flush();
            dataStreamToServer.close();

            BufferedReader dataStreamFromUrl = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String dataFromUrl = "";
            String dataBuffer = "";

            while ((dataBuffer = dataStreamFromUrl.readLine()) != null) {
                dataFromUrl = dataFromUrl + dataBuffer;
            }

            dataStreamFromUrl.close();
            System.out.println("Response: " + dataFromUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static StringBuffer convertToUnicode(String regText) {
        char[] chars = regText.toCharArray();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            String iniHexString = Integer.toHexString(chars[i]);
            if (iniHexString.length() == 1) {
                iniHexString = "000" + iniHexString;
            } else if (iniHexString.length() == 2) {
                iniHexString = "00" + iniHexString;
            } else if (iniHexString.length() == 3) {
                iniHexString = "0" + iniHexString;
            }

            hexString.append(iniHexString);
        }
        System.out.println(hexString);
        return hexString;
    }
}
