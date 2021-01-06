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
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findByIdservice", query = "SELECT s FROM Service s WHERE s.idservice = :idservice"),
    @NamedQuery(name = "Service.findByCode", query = "SELECT s FROM Service s WHERE s.code = :code"),
    @NamedQuery(name = "Service.findByNom", query = "SELECT s FROM Service s WHERE s.nom = :nom"),
    @NamedQuery(name = "Service.findByDatecreation", query = "SELECT s FROM Service s WHERE s.datecreation = :datecreation"),
    @NamedQuery(name = "Service.findByResponsable", query = "SELECT s FROM Service s WHERE s.responsable = :responsable"),
    @NamedQuery(name = "Service.findByIdparent", query = "SELECT s FROM Service s WHERE s.idparent = :idparent"),
    @NamedQuery(name = "Service.findByCentral", query = "SELECT s FROM Service s WHERE s.central = :central"),
    @NamedQuery(name = "Service.findByRegional", query = "SELECT s FROM Service s WHERE s.regional = :regional")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idservice;
    @Size(max = 254)
    private String code;
    @Size(max = 254)
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date datecreation;
    @Size(max = 254)
    private String responsable;
    private Integer idparent;
    private Boolean central;
    private Boolean regional;
    @OneToMany(mappedBy = "idservice", fetch = FetchType.LAZY)
    private List<Projetservice> projetserviceList;
    @JoinColumn(name = "idaddresse", referencedColumnName = "idaddresse")
    @ManyToOne(fetch = FetchType.LAZY)
    private Addresse idaddresse;
    @OneToMany(mappedBy = "idservice", fetch = FetchType.LAZY)
    private List<Acteur> acteurList;
    private Boolean visibilitesuivi;

    public Service() {
    }

    public Service(Integer idservice) {
        this.idservice = idservice;
    }

    public Integer getIdservice() {
        return idservice;
    }

    public void setIdservice(Integer idservice) {
        this.idservice = idservice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Integer getIdparent() {
        return idparent;
    }

    public void setIdparent(Integer idparent) {
        this.idparent = idparent;
    }

    public Boolean getCentral() {
        return central;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public Boolean getRegional() {
        return regional;
    }

    public void setRegional(Boolean regional) {
        this.regional = regional;
    }

    public Boolean getVisibilitesuivi() {
        return visibilitesuivi;
    }

    public void setVisibilitesuivi(Boolean visibilitesuivi) {
        this.visibilitesuivi = visibilitesuivi;
    }

    @XmlTransient
    public List<Projetservice> getProjetserviceList() {
        return projetserviceList;
    }

    public void setProjetserviceList(List<Projetservice> projetserviceList) {
        this.projetserviceList = projetserviceList;
    }

    public Addresse getIdaddresse() {
        return idaddresse;
    }

    public void setIdaddresse(Addresse idaddresse) {
        this.idaddresse = idaddresse;
    }

    @XmlTransient
    public List<Acteur> getActeurList() {
        return acteurList;
    }

    public void setActeurList(List<Acteur> acteurList) {
        this.acteurList = acteurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idservice != null ? idservice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.idservice == null && other.idservice != null) || (this.idservice != null && !this.idservice.equals(other.idservice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Service[ idservice=" + idservice + " ]";
    }

}
