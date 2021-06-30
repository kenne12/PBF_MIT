package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AllmySms {

    private String phoneNumber = "";
    private static final String sender = "CTNPBF";
    private String message = "";
    private static final String indicatif = "+237";

    public AllmySms(String numero, String message) {
        this.phoneNumber = ("+237" + numero);
        this.message = message;
    }

    public void send() {
        try {
            this.message = URLEncoder.encode(this.message, "UTF-8");
            String login = "batrapi";
            String apiKey = "njuomshetku";
            String smsData = "<DATA><MESSAGE><![CDATA[[" + this.message + "]]></MESSAGE><TPOA>" + this.sender + "</TPOA><SMS><MOBILEPHONE>" + this.phoneNumber + "</MOBILEPHONE></SMS></DATA>";

            String url = "https://api.allmysms.com/http/9.0/sendSms/?login=" + login + "&apiKey=" + apiKey + "&smsData=" + smsData;

            URL client = new URL(url);
            URLConnection conn = client.openConnection();
            InputStream responseBody = conn.getInputStream();

            byte[] contents = new byte[1024];

            int bytesRead = 0;
            String strFileContents = null;
            while ((bytesRead = responseBody.read(contents)) != -1) {
                strFileContents = new String(contents, 0, bytesRead);
            }

            responseBody.close();
            System.out.println(strFileContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized String send(String message, String phoneNumber) {
        try {
            // String apiKey = "4a84eaf8565b912";
            // principal = beininfos / 78fefca6029a91b /YmVpbmluZm9zOjc4ZmVmY2E2MDI5YTkxYg==
            // secondary = collegeco / 1234colco@ / apikey = 4a84eaf8565b912
            // token primary = YmVpbmluZm9zOjc4ZmVmY2E2MDI5YTkxYg==
            // token secondary = Y29sbGVnZWNvOjRhODRlYWY4NTY1YjkxMg==
            String message_encode = URLEncoder.encode(message, "UTF-8");
            String login = "beininfos";
            String apiKey = "78fefca6029a91b";
            
            System.err.println("envoyé");

            // https://api.allmysms.com/http/9.0/?login=collegeco&apiKey=78fefca6029a91b&message=Bonjour,%20Merci%20d%27utiliser%20allmysms.com%20STOP%20au%2036180&mobile=237673564186&tpoa=kenne
            String smsData = "<DATA><MESSAGE><![CDATA[" + message_encode + "]]></MESSAGE><TPOA>" + sender + "</TPOA><SMS><MOBILEPHONE>" + indicatif + "" + phoneNumber + "</MOBILEPHONE></SMS></DATA>";

            String url = "https://api.allmysms.com/http/9.0/sendSms/?login=" + login + "&apiKey=" + apiKey + "&smsData=" + smsData;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //optional default is GET
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //int responseCode = con.getResponseCode();
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
