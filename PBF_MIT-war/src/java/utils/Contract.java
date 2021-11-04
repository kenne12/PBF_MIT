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
public class Contract {

    private String service;
    private String contractDescription;
    private List<ServiceContract> serviceContracts;

    public Contract() {
        this.service = "";
        this.contractDescription = "";
        serviceContracts = new ArrayList<>();
    }

    public Contract(String service, String contractDescription, List<ServiceContract> serviceContracts) {
        this.service = service;
        this.contractDescription = contractDescription;
        this.serviceContracts = serviceContracts;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public List<ServiceContract> getServiceContracts() {
        return serviceContracts;
    }

    public void setServiceContracts(List<ServiceContract> serviceContracts) {
        this.serviceContracts = serviceContracts;
    }

    @Override
    public String toString() {
        return "Contract{" + "service=" + service + ", contractDescription=" + contractDescription + ", serviceContracts=" + serviceContracts + '}';
    }

}
