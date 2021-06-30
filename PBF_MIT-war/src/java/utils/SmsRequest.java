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
public class SmsRequest {

    private String subject;
    private String text;
    private String sender;
    private ReceipientSms receipientSms;
    private List<ReceipientSms> receipients;

    public SmsRequest() {
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

    public ReceipientSms getReceipientSms() {
        return receipientSms;
    }

    public void setReceipientSms(ReceipientSms receipientSms) {
        this.receipientSms = receipientSms;
    }

    public List<ReceipientSms> getReceipients() {
        return receipients;
    }

    public void setReceipients(List<ReceipientSms> receipients) {
        this.receipients = receipients;
    }

}
