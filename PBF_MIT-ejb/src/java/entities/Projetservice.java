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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Projetservice.findAll", query = "SELECT p FROM Projetservice p"),
    @NamedQuery(name = "Projetservice.findByIdprojetservice", query = "SELECT p FROM Projetservice p WHERE p.idprojetservice = :idprojetservice")})
public class Projetservice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idprojetservice;
    @JoinColumn(name = "idprojet", referencedColumnName = "idprojet")
    @ManyToOne(fetch = FetchType.LAZY)
    private Projet idprojet;
    @JoinColumn(name = "idservice", referencedColumnName = "idservice")
    @ManyToOne(fetch = FetchType.LAZY)
    private Service idservice;
    @OneToMany(mappedBy = "idprojetservice", fetch = FetchType.LAZY)
    private List<Programmation> programmationList;

    public Projetservice() {
    }

    public Projetservice(Long idprojetservice) {
        this.idprojetservice = idprojetservice;
    }

    public Long getIdprojetservice() {
        return idprojetservice;
    }

    public void setIdprojetservice(Long idprojetservice) {
        this.idprojetservice = idprojetservice;
    }

    public Projet getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(Projet idprojet) {
        this.idprojet = idprojet;
    }

    public Service getIdservice() {
        return idservice;
    }

    public void setIdservice(Service idservice) {
        this.idservice = idservice;
    }

    @XmlTransient
    public List<Programmation> getProgrammationList() {
        return programmationList;
    }

    public void setProgrammationList(List<Programmation> programmationList) {
        this.programmationList = programmationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprojetservice != null ? idprojetservice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projetservice)) {
            return false;
        }
        Projetservice other = (Projetservice) object;
        if ((this.idprojetservice == null && other.idprojetservice != null) || (this.idprojetservice != null && !this.idprojetservice.equals(other.idprojetservice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Projetservice[ idprojetservice=" + idprojetservice + " ]";
    }
    
}
