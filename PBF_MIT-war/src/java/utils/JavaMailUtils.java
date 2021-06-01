/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author USER
 */
public class JavaMailUtils {

    private final static Properties properties = new Properties();

    public static synchronized void sendMail(EmailRequest emailRequest) {
        try {
            properties.put("mail.smtp.host", "mail12.lwspanel.com");
            //properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(properties, new AuthenticatorLws());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("contact@beininfoplus.com", "CTN PBF CAMEROON"));

            InternetAddress[] internetAddresses = new InternetAddress[emailRequest.receipients.size()];
            int i = 0;
            for (Receipient r : emailRequest.receipients) {
                internetAddresses[i] = new InternetAddress(r.getReceipient(), r.getTitle());
                i++;
            }

            message.addRecipients(Message.RecipientType.TO, internetAddresses);
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getText());
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
