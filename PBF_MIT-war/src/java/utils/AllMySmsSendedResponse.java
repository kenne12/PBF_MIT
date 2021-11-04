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
public class AllMySmsSendedResponse {

    private Integer code;
    private String description;
    private Integer alerting;
    private String invalidNumbers;
    private Integer nbContacts;
    private Integer nbSms;
    private Double balance;
    private Float cost;
    private String smsId;

    public AllMySmsSendedResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAlerting() {
        return alerting;
    }

    public void setAlerting(Integer alerting) {
        this.alerting = alerting;
    }

    public String getInvalidNumbers() {
        return invalidNumbers;
    }

    public void setInvalidNumbers(String invalidNumbers) {
        this.invalidNumbers = invalidNumbers;
    }

    public Integer getNbContacts() {
        return nbContacts;
    }

    public void setNbContacts(Integer nbContacts) {
        this.nbContacts = nbContacts;
    }

    public Integer getNbSms() {
        return nbSms;
    }

    public void setNbSms(Integer nbSms) {
        this.nbSms = nbSms;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    @Override
    public String toString() {
        return "AllMySmsSendedResponse{" + "code=" + code + ", description=" + description + ", alerting=" + alerting + ", invalidNumbers=" + invalidNumbers + ", nbContacts=" + nbContacts + ", nbSms=" + nbSms + ", balance=" + balance + ", cost=" + cost + ", smsId=" + smsId + '}';
    }

}
