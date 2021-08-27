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
public class SmsThread extends Thread {

    private SmsRequest smsRequest;
    private String mode;

    public SmsThread() {

    }

    public SmsThread(SmsRequest smsRequest) {
        this.smsRequest = smsRequest;
    }

    @Override
    public void run() {
        if (mode.equals("UNIC")) {
            AllmySms.send(smsRequest.getText(), smsRequest.getReceipientSms().getReceipient());
        } else if (mode.equals("MULTIPLE")) {
            smsRequest.getReceipients().stream().forEach((r) -> {
                AllmySms.send(smsRequest.getText(), r.getReceipient());
            });
        }
    }

    public void setSmslRequest(SmsRequest smsRequest) {
        this.smsRequest = smsRequest;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
