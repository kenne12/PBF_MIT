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
public class ObmSmsSendRequest {

    private String sender;
    private String message;
    private String mobiles;

    public ObmSmsSendRequest() {
    }

    public ObmSmsSendRequest(String sender, String message, String mobiles) {
        this.sender = sender;
        this.message = message;
        this.mobiles = mobiles;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

}
