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
    @NamedQuery(name = "Modealerte.findAll", query = "SELECT m FROM Modealerte m"),
    @NamedQuery(name = "Modealerte.findByIdmodealerte", query = "SELECT m FROM Modealerte m WHERE m.idmodealerte = :idmodealerte"),
    @NamedQuery(name = "Modealerte.findByNomfr", query = "SELECT m FROM Modealerte m WHERE m.nomfr = :nomfr"),
    @NamedQuery(name = "Modealerte.findByNomen", query = "SELECT m FROM Modealerte m WHERE m.nomen = :nomen")})
public class Modealerte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idmodealerte;
    @Size(max = 254)
    private String nomfr;
    @Size(max = 254)
    private String nomen;
    @OneToMany(mappedBy = "idmodealerte", fetch = FetchType.LAZY)
    private List<Planningrappel> planningrappelList;
    @OneToMany(mappedBy = "idmodealerte", fetch = FetchType.LAZY)
    private List<Alerte> alerteList;

    public Modealerte() {
    }

    public Modealerte(Integer idmodealerte) {
        this.idmodealerte = idmodealerte;
    }

    public Integer getIdmodealerte() {
        return idmodealerte;
    }

    public void setIdmodealerte(Integer idmodealerte) {
        this.idmodealerte = idmodealerte;
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
        hash += (idmodealerte != null ? idmodealerte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modealerte)) {
            return false;
        }
        Modealerte other = (Modealerte) object;
        if ((this.idmodealerte == null && other.idmodealerte != null) || (this.idmodealerte != null && !this.idmodealerte.equals(other.idmodealerte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Modealerte[ idmodealerte=" + idmodealerte + " ]";
    }
    
}
