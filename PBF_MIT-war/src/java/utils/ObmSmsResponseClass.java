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
public class ObmSmsResponseClass {

    private Integer sents;
    private Integer failed;
    private SmsSubResponse sms;

    public ObmSmsResponseClass() {
    }

    public ObmSmsResponseClass(Integer sents, Integer failed, SmsSubResponse sms) {
        this.sents = sents;
        this.failed = failed;
        this.sms = sms;
    }

    public Integer getSents() {
        return sents;
    }

    public void setSents(Integer sents) {
        this.sents = sents;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public SmsSubResponse getSms() {
        return sms;
    }

    public void setSms(SmsSubResponse sms) {
        this.sms = sms;
    }

}
