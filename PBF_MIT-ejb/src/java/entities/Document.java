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
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByIddocument", query = "SELECT d FROM Document d WHERE d.iddocument = :iddocument"),
    @NamedQuery(name = "Document.findByNom", query = "SELECT d FROM Document d WHERE d.nom = :nom"),
    @NamedQuery(name = "Document.findByDescription", query = "SELECT d FROM Document d WHERE d.description = :description"),
    @NamedQuery(name = "Document.findByCode", query = "SELECT d FROM Document d WHERE d.code = :code")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer iddocument;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String description;
    @Size(max = 2147483647)
    private String code;
    @OneToMany(mappedBy = "iddocument", fetch = FetchType.LAZY)
    private List<Etape> etapeList;
    @OneToMany(mappedBy = "iddocument", fetch = FetchType.LAZY)
    private List<Programmation> programmationList;

    public Document() {
    }

    public Document(Integer iddocument) {
        this.iddocument = iddocument;
    }

    public Integer getIddocument() {
        return iddocument;
    }

    public void setIddocument(Integer iddocument) {
        this.iddocument = iddocument;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public List<Etape> getEtapeList() {
        return etapeList;
    }

    public void setEtapeList(List<Etape> etapeList) {
        this.etapeList = etapeList;
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
        hash += (iddocument != null ? iddocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.iddocument == null && other.iddocument != null) || (this.iddocument != null && !this.iddocument.equals(other.iddocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Document[ iddocument=" + iddocument + " ]";
    }
    
}
