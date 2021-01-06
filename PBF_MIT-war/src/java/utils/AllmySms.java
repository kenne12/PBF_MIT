package utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AllmySms {

    private String phoneNumber = "";
    private String sender = "CTNPBF";
    private String message = "";

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
}

