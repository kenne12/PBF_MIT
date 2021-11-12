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
public class ObmSmsAuthResponse {

    private String request_id;
    private Long time;
    private String access_token;
    private String token_type;
    private Integer expires_in;

    public ObmSmsAuthResponse() {
    }

    public ObmSmsAuthResponse(String request_id, Long time, String token_type, Integer expires_in) {
        this.request_id = request_id;
        this.time = time;
        this.token_type = token_type;
        this.expires_in = expires_in;
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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

}
