/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Objects;

/**
 *
 * @author USER
 */
public class AllMySmsAccountCheck {

    private Integer code;
    private String description;
    private Integer balance;
    private Integer nbSms;
    private String referenceCountryIso;
    private String apiKey;
    private String lastName;
    private String firstName;
    private String company;
    private String email;
    private Integer alerting;

    public AllMySmsAccountCheck() {
    }

    public AllMySmsAccountCheck(Integer code, String description, Integer balance, Integer nbSms, String referenceCountryIso, String apiKey, String lastName, String firstName, String company, String email, Integer alerting) {
        this.code = code;
        this.description = description;
        this.balance = balance;
        this.nbSms = nbSms;
        this.referenceCountryIso = referenceCountryIso;
        this.apiKey = apiKey;
        this.lastName = lastName;
        this.firstName = firstName;
        this.company = company;
        this.email = email;
        this.alerting = alerting;
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getNbSms() {
        return nbSms;
    }

    public void setNbSms(Integer nbSms) {
        this.nbSms = nbSms;
    }

    public String getReferenceCountryIso() {
        return referenceCountryIso;
    }

    public void setReferenceCountryIso(String referenceCountryIso) {
        this.referenceCountryIso = referenceCountryIso;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAlerting() {
        return alerting;
    }

    public void setAlerting(Integer alerting) {
        this.alerting = alerting;
    }

    @Override
    public String toString() {
        return "AllMySmsAccountCheck{" + "code=" + code + ", description=" + description + ", balance=" + balance + ", nbSms=" + nbSms + ", referenceCountryIso=" + referenceCountryIso + ", apiKey=" + apiKey + ", lastName=" + lastName + ", firstName=" + firstName + ", company=" + company + ", email=" + email + ", alerting=" + alerting + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.apiKey);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AllMySmsAccountCheck other = (AllMySmsAccountCheck) obj;
        if (!Objects.equals(this.apiKey, other.apiKey)) {
            return false;
        }
        return true;
    }

}
