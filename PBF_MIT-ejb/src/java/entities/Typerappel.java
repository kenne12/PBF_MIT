/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Typerappel.findAll", query = "SELECT t FROM Typerappel t"),
    @NamedQuery(name = "Typerappel.findByIdtyperappel", query = "SELECT t FROM Typerappel t WHERE t.idtyperappel = :idtyperappel"),
    @NamedQuery(name = "Typerappel.findByNomfr", query = "SELECT t FROM Typerappel t WHERE t.nomfr = :nomfr"),
    @NamedQuery(name = "Typerappel.findByNomen", query = "SELECT t FROM Typerappel t WHERE t.nomen = :nomen")})
public class Typerappel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idtyperappel;
    @Size(max = 254)
    private String nomfr;
    @Size(max = 254)
    private String nomen;
    @OneToMany(mappedBy = "idtyperappel", fetch = FetchType.LAZY)
    private List<Planningrappel> planningrappelList;
    @OneToMany(mappedBy = "idtyperappel", fetch = FetchType.LAZY)
    private List<Alerte> alerteList;

    public Typerappel() {
    }

    public Typerappel(Integer idtyperappel) {
        this.idtyperappel = idtyperappel;
    }

    public Integer getIdtyperappel() {
        return idtyperappel;
    }

    public void setIdtyperappel(Integer idtyperappel) {
        this.idtyperappel = idtyperappel;
    }

    public String getNomfr() {
        return nomfr;
    }

    public void setNomfr(String nomfr) {
        this.nomfr = nomfr;
    }

    public String getNomen() {
        return nomen;
    }

    public void setNomen(String nomen) {
        this.nomen = nomen;
    }

    @XmlTransient
    public List<Planningrappel> getPlanningrappelList() {
        return planningrappelList;
    }

    public void setPlanningrappelList(List<Planningrappel> planningrappelList) {
        this.planningrappelList = planningrappelList;
    }

    @XmlTransient
    public List<Alerte> getAlerteList() {
        return alerteList;
    }

    public void setAlerteList(List<Alerte> alerteList) {
        this.alerteList = alerteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtyperappel != null ? idtyperappel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typerappel)) {
            return false;
        }
        Typerappel other = (Typerappel) object;
        if ((this.idtyperappel == null && other.idtyperappel != null) || (this.idtyperappel != null && !this.idtyperappel.equals(other.idtyperappel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Typerappel[ idtyperappel=" + idtyperappel + " ]";
    }
    
}
