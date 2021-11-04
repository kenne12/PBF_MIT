/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
public class Notification implements Serializable {

    @Id
    @Basic(optional = false)
    private Integer idnotification;
    private boolean sms;
    private boolean mail;
    private String objet;
    private String message;
    @Column(name = "message_mail")
    private String messageMail;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_envoi")
    private Date dateEnvoi;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Acteur> acteurs;

    public Notification() {
        acteurs = new ArrayList<>();
    }

    public Notification(Integer idnotification) {
        acteurs = new ArrayList<>();
        this.idnotification = idnotification;
    }

    public Integer getIdnotification() {
        return idnotification;
    }

    public void setIdnotification(Integer idnotification) {
        this.idnotification = idnotification;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        this.mail = mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(Collection<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessageMail() {
        return messageMail;
    }

    public void setMessageMail(String messageMail) {
        this.messageMail = messageMail;
    }

}
