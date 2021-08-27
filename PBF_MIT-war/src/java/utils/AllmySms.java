package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AllmySms {

    private static final String sender = "CTN";

    public AllmySms() {

    }

    public static String send(String message, String phoneNumber) {
        try {
            String message_encode = URLEncoder.encode(message, "UTF-8");
            String apiAuthKey = "YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==";

            String url = "http://51.68.84.221:8096/api/sendJson?apiAuthKey=" + apiAuthKey + "&sender=" + sender + "&phoneNumber=" + phoneNumber + "&message=" + message_encode;
            System.err.println("url : " + url);

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //optional default is GET
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "00001";
        }
    }

    public static String sendOld(String message, String phoneNumber) {
        try {
            // principal = login = beininfos / password = (RAS) apiKey = 776c7594be8af30 / auth_key = YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==
            // auth_token primary = YmVpbmluZm9zOjc3NmM3NTk0YmU4YWYzMA==
            String message_encode = URLEncoder.encode(message, "UTF-8");
            String login = "beininfos";
            String apiKey = "78fefca6029a91b";

            String smsData = "<DATA><MESSAGE><![CDATA[" + message_encode + "]]></MESSAGE><TPOA>" + sender + "</TPOA><SMS><MOBILEPHONE>" + phoneNumber + "</MOBILEPHONE></SMS></DATA>";

            String url = "https://api.allmysms.com/http/9.0/sendSms/?login=" + login + "&apiKey=" + apiKey + "&smsData=" + smsData;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //optional default is GET
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "00001";
        }
    }
}
