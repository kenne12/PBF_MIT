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
public class ObmsmsBalanceResponse {

    private String request_id;
    private Long time;
    private String numbersms;

    public ObmsmsBalanceResponse() {
    }

    public ObmsmsBalanceResponse(String request_id, Long time, String numbersms) {
        this.request_id = request_id;
        this.time = time;
        this.numbersms = numbersms;
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

    public String getNumbersms() {
        return numbersms;
    }

    public void setNumbersms(String numbersms) {
        this.numbersms = numbersms;
    }

}
