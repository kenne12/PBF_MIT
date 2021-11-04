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
public class OrangeSmsSendedResponse {

    private List<String> address;
    private String senderAddress;
    private OutboundSMSTextMessage outboundSMSTextMessage;
    private String resourceURL;

    public OrangeSmsSendedResponse() {
        address = new ArrayList<>();
        outboundSMSTextMessage = new OutboundSMSTextMessage();
    }

    public OrangeSmsSendedResponse(List<String> address, String senderAddress, OutboundSMSTextMessage outboundSMSTextMessage, String resourceURL) {
        this.address = address;
        this.senderAddress = senderAddress;
        this.outboundSMSTextMessage = outboundSMSTextMessage;
        this.resourceURL = resourceURL;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
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

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    @Override
    public String toString() {
        return "OrangeSmsSendedResponse{" + "address=" + address + ", senderAddress=" + senderAddress + ", outboundSMSTextMessage=" + outboundSMSTextMessage + ", resourceURL=" + resourceURL + '}';
    }

}
