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
public class SmsThread {

    private SmsRequest smsRequest;
    private String mode;

    public SmsThread() {

    }

    public SmsThread(SmsRequest smsRequest) {
        this.smsRequest = smsRequest;
    }

    public List<String> run() {
        List<String> reports = new ArrayList<>();
        if (mode.equals("UNIC")) {
            String report = AllmySms.send(smsRequest.getText(), smsRequest.getReceipientSms().getReceipient());
            reports.add(report);
        } else if (mode.equals("MULTIPLE")) {
            smsRequest.getReceipients().stream().forEach((r) -> {
                String report = AllmySms.send(smsRequest.getText(), r.getReceipient());
                reports.add(report);
            });
        }
        return reports;
    }

    public String runSingle(SmsRequest smsRequest) {
        String report = AllmySms.send(smsRequest.getText(), smsRequest.getReceipientSms().getReceipient());
        return report;
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
