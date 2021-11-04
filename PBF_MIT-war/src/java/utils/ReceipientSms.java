/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Acteur;

/**
 *
 * @author USER
 */
public class ReceipientSms {

    private String receipient;
    private String title;
    private Acteur acteur;

    public ReceipientSms() {
    }

    public ReceipientSms(String receipient) {
        this.receipient = receipient;
    }

    public String getReceipient() {
        return receipient;
    }

    public void setReceipient(String receipient) {
        this.receipient = receipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

}
