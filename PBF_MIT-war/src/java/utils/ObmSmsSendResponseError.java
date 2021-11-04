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
public class ObmSmsSendResponseError {

    private String request_id;
    private Long time;
    private ObmSmsError error;

    public ObmSmsSendResponseError() {
    }

    public ObmSmsSendResponseError(String request_id, Long time, ObmSmsError error) {
        this.request_id = request_id;
        this.time = time;
        this.error = error;
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

    public ObmSmsError getError() {
        return error;
    }

    public void setError(ObmSmsError error) {
        this.error = error;
    }

}
