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
public class OutboundSMSMessageRequest {

    String address;
    String senderAddress;
    OutboundSMSTextMessage outboundSMSTextMessage;

    public OutboundSMSMessageRequest() {
        address = "";
        senderAddress = "";
        outboundSMSTextMessage = new OutboundSMSTextMessage();
    }

    public OutboundSMSMessageRequest(String address, String senderAddress, OutboundSMSTextMessage outboundSMSTextMessage) {
        this.address = address;
        this.senderAddress = senderAddress;
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public OutboundSMSTextMessage getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    public void setOutboundSMSTextMessage(OutboundSMSTextMessage outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

}
