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
public class ObmSmsSendResponse {

    private String request_id;
    private Long time;
    private ObmSmsResponseClass body;

    public ObmSmsSendResponse() {

    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ObmSmsResponseClass getBody() {
        return body;
    }

    public void setBody(ObmSmsResponseClass body) {
        this.body = body;
    }

}
