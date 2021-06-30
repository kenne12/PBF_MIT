/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author USER
 */
public class MailThread extends Thread {

    private EmailRequest emailRequest;

    public MailThread() {

    }

    public MailThread(EmailRequest emailRequest) {
        this.emailRequest = emailRequest;
    }

    @Override
    public void run() {
        System.err.println("mail thread executed");
        JavaMailUtils.sendMail(emailRequest);
    }

    public void setEmailRequest(EmailRequest emailRequest) {
        this.emailRequest = emailRequest;
    }

}
