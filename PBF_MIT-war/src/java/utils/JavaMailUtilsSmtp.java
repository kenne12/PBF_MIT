/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
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
public class JavaMailUtilsSmtp {

    private final static String MAILER_VERSION = "Java";
    private final static Properties prop = System.getProperties();

    public static boolean envoyerMailSMTP(EmailRequest emailRequest, boolean debug) {
        boolean result = false;
        try {
            prop.put("mail.smtp.host", "http://mail12.lwspanel.com");
            //properties.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(prop, new AuthenticatorLws());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("contact@beininfoplus.com", "CTN PBF CAMEROON"));

            InternetAddress[] internetAddresses = new InternetAddress[emailRequest.receipients.size()];
            int i = 0;
            for (Receipient r : emailRequest.receipients) {
                internetAddresses[i] = new InternetAddress(r.getReceipient(), r.getTitle());
                i++;
            }

            message.setRecipients(Message.RecipientType.TO, internetAddresses);
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getText());
            message.setHeader("X-Mailer", MAILER_VERSION);
            message.setSentDate(Date.from(Instant.now()));
            session.setDebug(debug);
            Transport.send(message);
            result = true;
        } catch (AddressException e) {
        } catch (MessagingException | UnsupportedEncodingException e) {
        }
        return result;
    }

}
