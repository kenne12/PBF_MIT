/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p"),
    @NamedQuery(name = "Projet.findByIdprojet", query = "SELECT p FROM Projet p WHERE p.idprojet = :idprojet"),
    @NamedQuery(name = "Projet.findByNom", query = "SELECT p FROM Projet p WHERE p.nom = :nom"),
    @NamedQuery(name = "Projet.findByEtat", query = "SELECT p FROM Projet p WHERE p.etat = :etat"),
    @NamedQuery(name = "Projet.findByCloture", query = "SELECT p FROM Projet p WHERE p.cloture = :cloture")})
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idprojet;
    @Size(max = 254)
    private String nom;
    private Boolean etat;
    private Boolean cloture;
    @Column(length = 50, nullable = false)
    private String repertoire;
    @Column(name = "lien_repertoire", length = 100, nullable = false)
    private String lienRepertoire;
    @OneToMany(mappedBy = "idprojet", fetch = FetchType.LAZY)
    private List<Projetservice> projetserviceList;
    @OneToMany(mappedBy = "idprojet", fetch = FetchType.LAZY)
    private List<Etapeprojet> etapeprojetList;
    @JoinColumn(name = "idperiode", referencedColumnName = "idperiode")
    @ManyToOne(fetch = FetchType.LAZY)
    private Periode idperiode;
    @Column(name = "notif_sms")
    private boolean notifSms;
    @Column(name = "notif_mail")
    private boolean notifMail;

    @Temporal(TemporalType.DATE)
    private Date datecreation;

    public Projet() {
    }

    public Projet(Integer idprojet) {
        this.idprojet = idprojet;
    }

    public Integer getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(Integer idprojet) {
        this.idprojet = idprojet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Boolean getCloture() {
        return cloture;
    }

    public void setCloture(Boolean cloture) {
        this.cloture = cloture;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getLienRepertoire() {
        return lienRepertoire;
    }

    public void setLienRepertoire(String lienRepertoire) {
        this.lienRepertoire = lienRepertoire;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public boolean isNotifSms() {
        return notifSms;
    }

    public void setNotifSms(boolean notifSms) {
        this.notifSms = notifSms;
    }

    public boolean isNotifMail() {
        return notifMail;
    }

    public void setNotifMail(boolean notifMail) {
        this.notifMail = notifMail;
    }

    @XmlTransient
    public List<Projetservice> getProjetserviceList() {
        return projetserviceList;
    }

    public void setProjetserviceList(List<Projetservice> projetserviceList) {
        this.projetserviceList = projetserviceList;
    }

    @XmlTransient
    public List<Etapeprojet> getEtapeprojetList() {
        return etapeprojetList;
    }

    public void setEtapeprojetList(List<Etapeprojet> etapeprojetList) {
        this.etapeprojetList = etapeprojetList;
    }

    public Periode getIdperiode() {
        return idperiode;
    }

    public void setIdperiode(Periode idperiode) {
        this.idperiode = idperiode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprojet != null ? idprojet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projet)) {
            return false;
        }
        Projet other = (Projet) object;
        if ((this.idprojet == null && other.idprojet != null) || (this.idprojet != null && !this.idprojet.equals(other.idprojet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Projet[ idprojet=" + idprojet + " ]";
    }

}
