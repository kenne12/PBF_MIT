/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alerte.findAll", query = "SELECT a FROM Alerte a"),
    @NamedQuery(name = "Alerte.findByIdalerte", query = "SELECT a FROM Alerte a WHERE a.idalerte = :idalerte"),
    @NamedQuery(name = "Alerte.findByMessage", query = "SELECT a FROM Alerte a WHERE a.message = :message"),
    @NamedQuery(name = "Alerte.findByDatealerte", query = "SELECT a FROM Alerte a WHERE a.datealerte = :datealerte"),
    @NamedQuery(name = "Alerte.findByHeurealerte", query = "SELECT a FROM Alerte a WHERE a.heurealerte = :heurealerte")})
public class Alerte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idalerte;
    @Size(max = 254)
    private String message;
    @Temporal(TemporalType.DATE)
    private Date datealerte;
    @Temporal(TemporalType.DATE)
    private Date heurealerte;
    @JoinColumn(name = "idmodealerte", referencedColumnName = "idmodealerte")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modealerte idmodealerte;
    @JoinColumn(name = "idtyperappel", referencedColumnName = "idtyperappel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Typerappel idtyperappel;

    public Alerte() {
    }

    public Alerte(Long idalerte) {
        this.idalerte = idalerte;
    }

    public Long getIdalerte() {
        return idalerte;
    }

    public void setIdalerte(Long idalerte) {
        this.idalerte = idalerte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatealerte() {
        return datealerte;
    }

    public void setDatealerte(Date datealerte) {
        this.datealerte = datealerte;
    }

    public Date getHeurealerte() {
        return heurealerte;
    }

    public void setHeurealerte(Date heurealerte) {
        this.heurealerte = heurealerte;
    }

    public Modealerte getIdmodealerte() {
        return idmodealerte;
    }

    public void setIdmodealerte(Modealerte idmodealerte) {
        this.idmodealerte = idmodealerte;
    }

    public Typerappel getIdtyperappel() {
        return idtyperappel;
    }

    public void setIdtyperappel(Typerappel idtyperappel) {
        this.idtyperappel = idtyperappel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalerte != null ? idalerte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alerte)) {
            return false;
        }
        Alerte other = (Alerte) object;
        if ((this.idalerte == null && other.idalerte != null) || (this.idalerte != null && !this.idalerte.equals(other.idalerte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Alerte[ idalerte=" + idalerte + " ]";
    }
    
}
