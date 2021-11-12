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
    @NamedQuery(name = "Periode.findAll", query = "SELECT p FROM Periode p"),
    @NamedQuery(name = "Periode.findByIdperiode", query = "SELECT p FROM Periode p WHERE p.idperiode = :idperiode"),
    @NamedQuery(name = "Periode.findByNom", query = "SELECT p FROM Periode p WHERE p.nom = :nom"),
    @NamedQuery(name = "Periode.findByEtat", query = "SELECT p FROM Periode p WHERE p.etat = :etat"),
    @NamedQuery(name = "Periode.findByIdparent", query = "SELECT p FROM Periode p WHERE p.idparent = :idparent"),
    @NamedQuery(name = "Periode.findByCode", query = "SELECT p FROM Periode p WHERE p.code = :code")})
public class Periode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idperiode;
    @Size(max = 254)
    private String nom;
    private Boolean etat;
    private Integer idparent;
    @Size(max = 2147483647)
    private String code;
    private int numero;
    private int niveau;
    @Column(name = "default_period")
    private boolean defaultPeriod;
    @OneToMany(mappedBy = "idperiode", fetch = FetchType.LAZY)
    private List<Projet> projetList;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    public Periode() {
    }

    public Periode(Integer idperiode) {
        this.idperiode = idperiode;
    }

    public Integer getIdperiode() {
        return idperiode;
    }

    public void setIdperiode(Integer idperiode) {
        this.idperiode = idperiode;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Integer getIdparent() {
        return idparent;
    }

    public void setIdparent(Integer idparent) {
        this.idparent = idparent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public boolean isDefaultPeriod() {
        return defaultPeriod;
    }

    public void setDefaultPeriod(boolean defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @XmlTransient
    public List<Projet> getProjetList() {
        return projetList;
    }

    public void setProjetList(List<Projet> projetList) {
        this.projetList = projetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperiode != null ? idperiode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periode)) {
            return false;
        }
        Periode other = (Periode) object;
        if ((this.idperiode == null && other.idperiode != null) || (this.idperiode != null && !this.idperiode.equals(other.idperiode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Periode[ idperiode=" + idperiode + " ]";
    }

}
