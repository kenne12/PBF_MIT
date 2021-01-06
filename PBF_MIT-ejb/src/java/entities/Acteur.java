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
    @NamedQuery(name = "Acteur.findAll", query = "SELECT a FROM Acteur a"),
    @NamedQuery(name = "Acteur.findByIdacteur", query = "SELECT a FROM Acteur a WHERE a.idacteur = :idacteur"),
    @NamedQuery(name = "Acteur.findByNom", query = "SELECT a FROM Acteur a WHERE a.nom = :nom"),
    @NamedQuery(name = "Acteur.findByPrenom", query = "SELECT a FROM Acteur a WHERE a.prenom = :prenom"),
    @NamedQuery(name = "Acteur.findBySexe", query = "SELECT a FROM Acteur a WHERE a.sexe = :sexe"),
    @NamedQuery(name = "Acteur.findByDatenaissance", query = "SELECT a FROM Acteur a WHERE a.datenaissance = :datenaissance"),
    @NamedQuery(name = "Acteur.findByLieunaissance", query = "SELECT a FROM Acteur a WHERE a.lieunaissance = :lieunaissance"),
    @NamedQuery(name = "Acteur.findByIddocument", query = "SELECT a FROM Acteur a WHERE a.iddocument = :iddocument")})
public class Acteur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idacteur;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String prenom;
    @Size(max = 254)
    private String sexe;
    @Temporal(TemporalType.DATE)
    private Date datenaissance;
    @Size(max = 254)
    private String lieunaissance;
    private Integer iddocument;
    @OneToMany(mappedBy = "idacteur", fetch = FetchType.LAZY)
    private List<Traitement> traitementList;
    @OneToMany(mappedBy = "idacteur", fetch = FetchType.LAZY)
    private List<Programmation> programmationList;
    @OneToMany(mappedBy = "idacteur", fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurList;
    @JoinColumn(name = "idaddresse", referencedColumnName = "idaddresse")
    @ManyToOne(fetch = FetchType.LAZY)
    private Addresse idaddresse;
    @JoinColumn(name = "idservice", referencedColumnName = "idservice")
    @ManyToOne(fetch = FetchType.LAZY)
    private Service idservice;

    public Acteur() {
    }

    public Acteur(Integer idacteur) {
        this.idacteur = idacteur;
    }

    public Integer getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(Integer idacteur) {
        this.idacteur = idacteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getLieunaissance() {
        return lieunaissance;
    }

    public void setLieunaissance(String lieunaissance) {
        this.lieunaissance = lieunaissance;
    }

    public Integer getIddocument() {
        return iddocument;
    }

    public void setIddocument(Integer iddocument) {
        this.iddocument = iddocument;
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

    @XmlTransient
    public List<Utilisateur> getUtilisateurList() {
        return utilisateurList;
    }

    public void setUtilisateurList(List<Utilisateur> utilisateurList) {
        this.utilisateurList = utilisateurList;
    }

    public Addresse getIdaddresse() {
        return idaddresse;
    }

    public void setIdaddresse(Addresse idaddresse) {
        this.idaddresse = idaddresse;
    }

    public Service getIdservice() {
        return idservice;
    }

    public void setIdservice(Service idservice) {
        this.idservice = idservice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacteur != null ? idacteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acteur)) {
            return false;
        }
        Acteur other = (Acteur) object;
        if ((this.idacteur == null && other.idacteur != null) || (this.idacteur != null && !this.idacteur.equals(other.idacteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Acteur[ idacteur=" + idacteur + " ]";
    }
    
}
