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
import javax.persistence.Column;
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
    @NamedQuery(name = "Etapeprojet.findAll", query = "SELECT e FROM Etapeprojet e"),
    @NamedQuery(name = "Etapeprojet.findByIdetapeprojet", query = "SELECT e FROM Etapeprojet e WHERE e.idetapeprojet = :idetapeprojet"),
    @NamedQuery(name = "Etapeprojet.findByNumero", query = "SELECT e FROM Etapeprojet e WHERE e.numero = :numero"),
    @NamedQuery(name = "Etapeprojet.findByDelai", query = "SELECT e FROM Etapeprojet e WHERE e.delai = :delai"),
    @NamedQuery(name = "Etapeprojet.findByDateetatinitial", query = "SELECT e FROM Etapeprojet e WHERE e.dateetatinitial = :dateetatinitial"),
    @NamedQuery(name = "Etapeprojet.findByIdetapeparent", query = "SELECT e FROM Etapeprojet e WHERE e.idetapeparent = :idetapeparent")})
public class Etapeprojet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idetapeprojet;
    private Integer numero;
    private Integer delai;
    @Column(length = 50, nullable = false)
    private String repertoire;
    @Column(name = "lien_repertoire", length = 100, nullable = false)
    private String lienRepertoire;
    @Temporal(TemporalType.DATE)
    private Date dateetatinitial;
    private Integer idetapeparent;
    @OneToMany(mappedBy = "idetapeprojet", fetch = FetchType.LAZY)
    private List<Traitement> traitementList;
    @OneToMany(mappedBy = "idetapeprojet", fetch = FetchType.LAZY)
    private List<Programmation> programmationList;
    @JoinColumn(name = "idetape", referencedColumnName = "idetape")
    @ManyToOne(fetch = FetchType.LAZY)
    private Etape idetape;
    @JoinColumn(name = "idprojet", referencedColumnName = "idprojet")
    @ManyToOne(fetch = FetchType.LAZY)
    private Projet idprojet;

    public Etapeprojet() {
    }

    public Etapeprojet(Long idetapeprojet) {
        this.idetapeprojet = idetapeprojet;
    }

    public Long getIdetapeprojet() {
        return idetapeprojet;
    }

    public void setIdetapeprojet(Long idetapeprojet) {
        this.idetapeprojet = idetapeprojet;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getDelai() {
        return delai;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getLienRepertoire() {
        return lienRepertoire;
    }

    public void setLienRepertoire(String lienRepertoire) {
        this.lienRepertoire = lienRepertoire;
    }

    public Date getDateetatinitial() {
        return dateetatinitial;
    }

    public void setDateetatinitial(Date dateetatinitial) {
        this.dateetatinitial = dateetatinitial;
    }

    public Integer getIdetapeparent() {
        return idetapeparent;
    }

    public void setIdetapeparent(Integer idetapeparent) {
        this.idetapeparent = idetapeparent;
    }

    @XmlTransient
    public List<Traitement> getTraitementList() {
        return traitementList;
    }

    public void setTraitementList(List<Traitement> traitementList) {
        this.traitementList = traitementList;
    }

    @XmlTransient
    public List<Programmation> getProgrammationList() {
        return programmationList;
    }

    public void setProgrammationList(List<Programmation> programmationList) {
        this.programmationList = programmationList;
    }

    public Etape getIdetape() {
        return idetape;
    }

    public void setIdetape(Etape idetape) {
        this.idetape = idetape;
    }

    public Projet getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(Projet idprojet) {
        this.idprojet = idprojet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idetapeprojet != null ? idetapeprojet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etapeprojet)) {
            return false;
        }
        Etapeprojet other = (Etapeprojet) object;
        if ((this.idetapeprojet == null && other.idetapeprojet != null) || (this.idetapeprojet != null && !this.idetapeprojet.equals(other.idetapeprojet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Etapeprojet[ idetapeprojet=" + idetapeprojet + " ]";
    }

}
