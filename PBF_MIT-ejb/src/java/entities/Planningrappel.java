/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planningrappel.findAll", query = "SELECT p FROM Planningrappel p"),
    @NamedQuery(name = "Planningrappel.findByIdplanningrappel", query = "SELECT p FROM Planningrappel p WHERE p.idplanningrappel = :idplanningrappel"),
    @NamedQuery(name = "Planningrappel.findByNombrejr", query = "SELECT p FROM Planningrappel p WHERE p.nombrejr = :nombrejr"),
    @NamedQuery(name = "Planningrappel.findByOccurence", query = "SELECT p FROM Planningrappel p WHERE p.occurence = :occurence"),
    @NamedQuery(name = "Planningrappel.findByIntervalrappel", query = "SELECT p FROM Planningrappel p WHERE p.intervalrappel = :intervalrappel")})
public class Planningrappel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idplanningrappel;
    private Integer nombrejr;
    private Integer occurence;
    private Integer intervalrappel;
    @JoinColumn(name = "idmodealerte", referencedColumnName = "idmodealerte")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modealerte idmodealerte;
    @JoinColumn(name = "idtyperappel", referencedColumnName = "idtyperappel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Typerappel idtyperappel;

    public Planningrappel() {
    }

    public Planningrappel(Long idplanningrappel) {
        this.idplanningrappel = idplanningrappel;
    }

    public Long getIdplanningrappel() {
        return idplanningrappel;
    }

    public void setIdplanningrappel(Long idplanningrappel) {
        this.idplanningrappel = idplanningrappel;
    }

    public Integer getNombrejr() {
        return nombrejr;
    }

    public void setNombrejr(Integer nombrejr) {
        this.nombrejr = nombrejr;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public Integer getIntervalrappel() {
        return intervalrappel;
    }

    public void setIntervalrappel(Integer intervalrappel) {
        this.intervalrappel = intervalrappel;
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
        hash += (idplanningrappel != null ? idplanningrappel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planningrappel)) {
            return false;
        }
        Planningrappel other = (Planningrappel) object;
        if ((this.idplanningrappel == null && other.idplanningrappel != null) || (this.idplanningrappel != null && !this.idplanningrappel.equals(other.idplanningrappel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Planningrappel[ idplanningrappel=" + idplanningrappel + " ]";
    }
    
}
