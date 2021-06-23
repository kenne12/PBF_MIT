/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
    @NamedQuery(name = "Etape.findAll", query = "SELECT e FROM Etape e"),
    @NamedQuery(name = "Etape.findByIdetape", query = "SELECT e FROM Etape e WHERE e.idetape = :idetape"),
    @NamedQuery(name = "Etape.findByNom", query = "SELECT e FROM Etape e WHERE e.nom = :nom"),
    @NamedQuery(name = "Etape.findByCode", query = "SELECT e FROM Etape e WHERE e.code = :code"),
    @NamedQuery(name = "Etape.findByDelaiDefault", query = "SELECT e FROM Etape e WHERE e.delaiDefault = :delaiDefault")})
public class Etape implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idetape;
    @Size(max = 2147483647)
    private String nom;
    @Size(max = 2147483647)
    private String code;
    private String repertoire;
    @Column(name = "delai_default")
    private Integer delaiDefault;
    @JoinColumn(name = "iddocument", referencedColumnName = "iddocument")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document iddocument;
    @OneToMany(mappedBy = "idetape", fetch = FetchType.LAZY)
    private List<Etapeprojet> etapeprojetList;

    public Etape() {
    }

    public Etape(Integer idetape) {
        this.idetape = idetape;
    }

    public Integer getIdetape() {
        return idetape;
    }

    public void setIdetape(Integer idetape) {
        this.idetape = idetape;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDelaiDefault() {
        return delaiDefault;
    }

    public void setDelaiDefault(Integer delaiDefault) {
        this.delaiDefault = delaiDefault;
    }

    public Document getIddocument() {
        return iddocument;
    }

    public void setIddocument(Document iddocument) {
        this.iddocument = iddocument;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public List<Etapeprojet> getEtapeprojetList() {
        return etapeprojetList;
    }

    public void setEtapeprojetList(List<Etapeprojet> etapeprojetList) {
        this.etapeprojetList = etapeprojetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idetape != null ? idetape.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etape)) {
            return false;
        }
        Etape other = (Etape) object;
        if ((this.idetape == null && other.idetape != null) || (this.idetape != null && !this.idetape.equals(other.idetape))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Etape[ idetape=" + idetape + " ]";
    }

}
