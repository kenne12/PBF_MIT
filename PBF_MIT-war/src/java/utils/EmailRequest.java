/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class EmailRequest {

    private String subject;
    private String text;
    private String sender;
    List<Receipient> receipients;

    public EmailRequest() {
        receipients = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<Receipient> getReceipients() {
        return receipients;
    }

    public void setReceipients(List<Receipient> receipients) {
        this.receipients = receipients;
    }

}
