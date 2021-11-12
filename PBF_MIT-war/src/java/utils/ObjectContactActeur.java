/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Acteur;
import java.util.Objects;

/**
 *
 * @author USER
 */
public class ObjectContactActeur {

    private String contact;
    private Acteur acteur;

    public ObjectContactActeur() {
    }

    public ObjectContactActeur(String contact, Acteur acteur) {
        this.contact = contact;
        this.acteur = acteur;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.contact);
        hash = 79 * hash + Objects.hashCode(this.acteur);
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
        final ObjectContactActeur other = (ObjectContactActeur) obj;
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.acteur, other.acteur)) {
            return false;
        }
        return true;
    }

}
