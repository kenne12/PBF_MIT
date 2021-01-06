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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Traitement.findAll", query = "SELECT t FROM Traitement t"),
    @NamedQuery(name = "Traitement.findByIdtraitement", query = "SELECT t FROM Traitement t WHERE t.idtraitement = :idtraitement"),
    @NamedQuery(name = "Traitement.findByDateprevinitial", query = "SELECT t FROM Traitement t WHERE t.dateprevinitial = :dateprevinitial"),
    @NamedQuery(name = "Traitement.findByDateenvoieffectif", query = "SELECT t FROM Traitement t WHERE t.dateenvoieffectif = :dateenvoieffectif"),
    @NamedQuery(name = "Traitement.findByValidee", query = "SELECT t FROM Traitement t WHERE t.validee = :validee")})
public class Traitement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idtraitement;
    @Temporal(TemporalType.DATE)
    private Date dateprevinitial;
    @Temporal(TemporalType.DATE)
    private Date dateenvoieffectif;
    private Boolean validee;
    @JoinColumn(name = "idacteur", referencedColumnName = "idacteur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Acteur idacteur;
    @JoinColumn(name = "idetapeprojet", referencedColumnName = "idetapeprojet")
    @ManyToOne(fetch = FetchType.LAZY)
    private Etapeprojet idetapeprojet;
    @OneToMany(mappedBy = "idtraitement", fetch = FetchType.LAZY)
    private List<Piecejointes> piecejointesList;

    public Traitement() {
    }

    public Traitement(Long idtraitement) {
        this.idtraitement = idtraitement;
    }

    public Long getIdtraitement() {
        return idtraitement;
    }

    public void setIdtraitement(Long idtraitement) {
        this.idtraitement = idtraitement;
    }

    public Date getDateprevinitial() {
        return dateprevinitial;
    }

    public void setDateprevinitial(Date dateprevinitial) {
        this.dateprevinitial = dateprevinitial;
    }

    public Date getDateenvoieffectif() {
        return dateenvoieffectif;
    }

    public void setDateenvoieffectif(Date dateenvoieffectif) {
        this.dateenvoieffectif = dateenvoieffectif;
    }

    public Boolean getValidee() {
        return validee;
    }

    public void setValidee(Boolean validee) {
        this.validee = validee;
    }

    public Acteur getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(Acteur idacteur) {
        this.idacteur = idacteur;
    }

    public Etapeprojet getIdetapeprojet() {
        return idetapeprojet;
    }

    public void setIdetapeprojet(Etapeprojet idetapeprojet) {
        this.idetapeprojet = idetapeprojet;
    }

    @XmlTransient
    public List<Piecejointes> getPiecejointesList() {
        return piecejointesList;
    }

    public void setPiecejointesList(List<Piecejointes> piecejointesList) {
        this.piecejointesList = piecejointesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtraitement != null ? idtraitement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traitement)) {
            return false;
        }
        Traitement other = (Traitement) object;
        if ((this.idtraitement == null && other.idtraitement != null) || (this.idtraitement != null && !this.idtraitement.equals(other.idtraitement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Traitement[ idtraitement=" + idtraitement + " ]";
    }
    
}
