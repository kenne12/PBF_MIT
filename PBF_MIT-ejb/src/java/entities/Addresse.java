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
    @NamedQuery(name = "Addresse.findAll", query = "SELECT a FROM Addresse a"),
    @NamedQuery(name = "Addresse.findByIdaddresse", query = "SELECT a FROM Addresse a WHERE a.idaddresse = :idaddresse"),
    @NamedQuery(name = "Addresse.findByTelephone1", query = "SELECT a FROM Addresse a WHERE a.telephone1 = :telephone1"),
    @NamedQuery(name = "Addresse.findByTelephone2", query = "SELECT a FROM Addresse a WHERE a.telephone2 = :telephone2"),
    @NamedQuery(name = "Addresse.findByAdresse", query = "SELECT a FROM Addresse a WHERE a.adresse = :adresse"),
    @NamedQuery(name = "Addresse.findByEmail", query = "SELECT a FROM Addresse a WHERE a.email = :email"),
    @NamedQuery(name = "Addresse.findBySiteweb", query = "SELECT a FROM Addresse a WHERE a.siteweb = :siteweb"),
    @NamedQuery(name = "Addresse.findByBp", query = "SELECT a FROM Addresse a WHERE a.bp = :bp")})
public class Addresse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idaddresse;
    @Size(max = 254)
    private String telephone1;
    @Size(max = 254)
    private String telephone2;
    @Size(max = 254)
    private String adresse;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 254)
    private String email;
    @Size(max = 254)
    private String siteweb;
    @Size(max = 254)
    private String bp;
    @OneToMany(mappedBy = "idaddresse", fetch = FetchType.LAZY)
    private List<Service> serviceList;
    @OneToMany(mappedBy = "idaddresse", fetch = FetchType.LAZY)
    private List<Acteur> acteurList;

    public Addresse() {
    }

    public Addresse(Long idaddresse) {
        this.idaddresse = idaddresse;
    }

    public Long getIdaddresse() {
        return idaddresse;
    }

    public void setIdaddresse(Long idaddresse) {
        this.idaddresse = idaddresse;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    @XmlTransient
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
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
        hash += (idaddresse != null ? idaddresse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addresse)) {
            return false;
        }
        Addresse other = (Addresse) object;
        if ((this.idaddresse == null && other.idaddresse != null) || (this.idaddresse != null && !this.idaddresse.equals(other.idaddresse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Addresse[ idaddresse=" + idaddresse + " ]";
    }
    
}
