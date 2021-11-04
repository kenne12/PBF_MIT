package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    public static Map treatResponse(String response, Integer api) {

        Map m = new HashMap();

        if (response.equals("00001")) {
            m = fieldResponse(false, "00001", "ECHEC D'ENVOI, MACHINE NON CONNECTE A INTERNET");
            return m;
        }

        if (api.equals(2)) {
            if (response.contains("Not enough credit")) {
                m = fieldResponse(false, "104", "SOLDE ETABLISSEMENT INSUFFISANT - ORANGE SMS API");
            } else {
                response = Utilitaires.removeFirstJsonChar(response, "outboundSMSMessageRequest");
                OrangeSmsSendedResponse orangeSmsSendedResponse;

                orangeSmsSendedResponse = (OrangeSmsSendedResponse) CheckAccount.fromJsonToObject(response, new OrangeSmsSendedResponse());
                if (orangeSmsSendedResponse != null) {
                    m = fieldResponse(true, "201", "ENVOYE AVEC SUCCESS - Orange CM SMS - API");
                } else {
                    m = fieldResponse(false, "00000", "ECHEC D'ENVOI, AUTRES CAUSES - Orange CM SMS - API");
                }
            }
        } else {
            if (api.equals(3)) {

                ObmSmsSendResponse obmSmsSendResponse = (ObmSmsSendResponse) CheckAccount.fromJsonToObject(response, new ObmSmsSendResponse());
                if (obmSmsSendResponse != null) {
                    if (obmSmsSendResponse.getBody().getSents().equals(1)) {
                        m = fieldResponse(true, "200", "ENVOYE AVEC SUCCESS - OBMAFRICA SMS - API");
                    } else {
                        m = fieldResponse(false, "00000", "ECHEC D'ENVOI, AUTRES CAUSES - OBMAFRICA SMS - API");
                    }
                } else {
                    if (response.contains("\"code\":\"1007\"")) {
                        m = fieldResponse(false, "1007", "NUMERO INVALIDE - OBMAFRICA SMS - API");
                    } else {
                        ObmSmsSendResponseError error = (ObmSmsSendResponseError) CheckAccount.fromJsonToObject(response, new ObmSmsSendResponseError());
                        if (error != null) {
                            m = fieldResponse(false, error.getError().getCode(), error.getError().getMessage());
                        } else {
                            m = fieldResponse(false, "00000", "ECHEC D'ENVOI, AUTRES CAUSES - OBMAFRICA SMS - API");
                        }
                    }
                }
            }

            if (api.equals(1)) {
                AllMySmsSendedResponse allMySmsSendedResponse = (AllMySmsSendedResponse) CheckAccount.fromJsonToObject(response, new AllMySmsSendedResponse());
                if (allMySmsSendedResponse != null) {
                    if (allMySmsSendedResponse.getCode() == 101) {
                        m = fieldResponse(true, "101", "ENVOYE AVEC SUCCESS - AllMySMS - API");
                    } else {
                        m = treatOtherCodeApiAllMySms(allMySmsSendedResponse);
                    }
                } else {
                    m = treatOtherCodeApiAllMySms(response);
                }
            }
        }
        return m;
    }

    public static String treatContact(String item) {
        String contact = item;
        contact = contact.replaceAll(" ", "");

        if (contact.length() == 9) {
            return contact;
        } else if (contact.length() > 9) {
            String[] chaines = null;
            if (contact.contains("/")) {
                chaines = contact.split("/");
            } else if (contact.contains(";")) {
                chaines = contact.split(";");
            } else if (contact.contains(",")) {
                chaines = contact.split(",");
            }
            if (chaines != null) {
                for (int count = 0; count < chaines.length; count++) {
                    if (chaines[count].length() == 9) {
                        return chaines[count];
                    }
                }
            }
        }
        return item;
    }

    private static Map fieldResponse(boolean etat, String code, String message) {
        Map map = new HashMap();
        map.put("etat", etat);
        map.put("code", code);
        map.put("message", message);
        return map;
    }

    private static Map treatOtherCodeApiAllMySms(AllMySmsSendedResponse allMySmsSendedResponse) {
        Map map = new HashMap();
        if (allMySmsSendedResponse.getCode().equals(114)) {
            map = fieldResponse(false, "114", "ECHEC D'ENVOI, NUMERO INVALIDE");
        } else if (allMySmsSendedResponse.getCode().equals(104)) {
            map = fieldResponse(false, "104", "ECHEC D'ENVOI, SOLDE ETABLISSEMENT INSUFFISANT - All My SMS");
        } else if (allMySmsSendedResponse.getCode().equals(121)) {
            map = fieldResponse(false, "121", "ECHEC D'ENVOI, REQUETTE DEJA TRANSMISE");
        } else {
            map = fieldResponse(false, "00000", "ECHEC D'ENVOI, AUTRES CAUSES");
        }
        return map;
    }

    private static Map treatOtherCodeApiAllMySms(String response) {
        Map map = new HashMap();
        if (response.contains("114")) {
            map = fieldResponse(false, "114", "ECHEC D'ENVOI, NUMERO INVALIDE");
        } else if (response.contains("104")) {
            map = fieldResponse(false, "104", "ECHEC D'ENVOI, SOLDE ETABLISSEMENT INSUFFISANT");
        } else if (response.contains("121")) {
            map = fieldResponse(false, "121", "ECHEC D'ENVOI, REQUETTE DEJA TRANSMISE");
        } else {
            map = fieldResponse(false, "00000", "ECHEC D'ENVOI, AUTRES CAUSES");
        }
        return map;
    }
}
