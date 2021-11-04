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
public class ServiceContract {

    private String country;
    private String service;
    private String contractId;
    private Integer availableUnits;
    private String expires;
    private String scDescription;

    public ServiceContract() {
    }

    public ServiceContract(String country, String service, String contractId, Integer availableUnits, String expires, String scDescription) {
        this.country = country;
        this.service = service;
        this.contractId = contractId;
        this.availableUnits = availableUnits;
        this.expires = expires;
        this.scDescription = scDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(Integer availableUnits) {
        this.availableUnits = availableUnits;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getScDescription() {
        return scDescription;
    }

    public void setScDescription(String scDescription) {
        this.scDescription = scDescription;
    }

    @Override
    public String toString() {
        return "ServiceContract{" + "country=" + country + ", service=" + service + ", contractId=" + contractId + ", availableUnits=" + availableUnits + ", expires=" + expires + ", scDescription=" + scDescription + '}';
    }

}
