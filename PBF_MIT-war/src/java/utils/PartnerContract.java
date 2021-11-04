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
public class PartnerContract {

    private String partnerId;
    private List<Contract> contracts;

    public PartnerContract() {
        partnerId = "";
        contracts = new ArrayList<>();
    }

    public PartnerContract(String partnerId, List<Contract> contracts) {
        this.partnerId = partnerId;
        this.contracts = contracts;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "PartnerContract{" + "partnerId=" + partnerId + ", contracts=" + contracts + '}';
    }

}
