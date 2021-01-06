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
    @NamedQuery(name = "Programmation.findAll", query = "SELECT p FROM Programmation p"),
    @NamedQuery(name = "Programmation.findByIdprogrammation", query = "SELECT p FROM Programmation p WHERE p.idprogrammation = :idprogrammation"),
    @NamedQuery(name = "Programmation.findByDateprevisionnel", query = "SELECT p FROM Programmation p WHERE p.dateprevisionnel = :dateprevisionnel"),
    @NamedQuery(name = "Programmation.findByDaterealisation", query = "SELECT p FROM Programmation p WHERE p.daterealisation = :daterealisation"),
    @NamedQuery(name = "Programmation.findByValide", query = "SELECT p FROM Programmation p WHERE p.valide = :valide"),
    @NamedQuery(name = "Programmation.findByEnvoye", query = "SELECT p FROM Programmation p WHERE p.envoye = :envoye"),
    @NamedQuery(name = "Programmation.findByChemin", query = "SELECT p FROM Programmation p WHERE p.chemin = :chemin"),
    @NamedQuery(name = "Programmation.findByTypefichier", query = "SELECT p FROM Programmation p WHERE p.typefichier = :typefichier"),
    @NamedQuery(name = "Programmation.findByActive", query = "SELECT p FROM Programmation p WHERE p.active = :active"),
    @NamedQuery(name = "Programmation.findByDateValidation", query = "SELECT p FROM Programmation p WHERE p.dateValidation = :dateValidation"),
    @NamedQuery(name = "Programmation.findByRetard", query = "SELECT p FROM Programmation p WHERE p.retard = :retard"),
    @NamedQuery(name = "Programmation.findByObservation", query = "SELECT p FROM Programmation p WHERE p.observation = :observation"),
    @NamedQuery(name = "Programmation.findByObservationarchivee", query = "SELECT p FROM Programmation p WHERE p.observationarchivee = :observationarchivee"),
    @NamedQuery(name = "Programmation.findByObservee", query = "SELECT p FROM Programmation p WHERE p.observee = :observee"),
    @NamedQuery(name = "Programmation.findByObservationvalidee", query = "SELECT p FROM Programmation p WHERE p.observationvalidee = :observationvalidee"),
    @NamedQuery(name = "Programmation.findByConteur", query = "SELECT p FROM Programmation p WHERE p.conteur = :conteur"),
    @NamedQuery(name = "Programmation.findByObservationutilisateur", query = "SELECT p FROM Programmation p WHERE p.observationutilisateur = :observationutilisateur")})
public class Programmation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idprogrammation;
    @Temporal(TemporalType.DATE)
    private Date dateprevisionnel;
    @Temporal(TemporalType.DATE)
    private Date daterealisation;
    private Boolean valide;
    private Boolean envoye;
    @Size(max = 2147483647)
    private String chemin;
    @Size(max = 2147483647)
    private String typefichier;
    private Boolean active;
    @Column(name = "date_validation")
    @Temporal(TemporalType.DATE)
    private Date dateValidation;
    private Integer retard;
    @Size(max = 2147483647)
    private String observation;
    @Size(max = 2147483647)
    private String observationarchivee;
    private Boolean observee;
    private Boolean observationvalidee;
    private Integer conteur;
    @Size(max = 2147483647)
    private String observationutilisateur;
    @Column(name = "date_transfert")
    @Temporal(TemporalType.DATE)
    private Date dateTransfert;

    @Column(name = "date_fin_previsionnel")
    @Temporal(TemporalType.DATE)
    private Date dateFinPrevisionnel;

    @JoinColumn(name = "idacteur", referencedColumnName = "idacteur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Acteur idacteur;
    @JoinColumn(name = "iddocument", referencedColumnName = "iddocument")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document iddocument;
    @JoinColumn(name = "idetapeprojet", referencedColumnName = "idetapeprojet")
    @ManyToOne(fetch = FetchType.LAZY)
    private Etapeprojet idetapeprojet;
    @JoinColumn(name = "idprojetservice", referencedColumnName = "idprojetservice")
    @ManyToOne(fetch = FetchType.LAZY)
    private Projetservice idprojetservice;
    @OneToMany(mappedBy = "idprogrammation", fetch = FetchType.LAZY)
    private List<Piecejointes> piecejointesList;

    public Programmation() {
    }

    public Programmation(Long idprogrammation) {
        this.idprogrammation = idprogrammation;
    }

    public Long getIdprogrammation() {
        return idprogrammation;
    }

    public void setIdprogrammation(Long idprogrammation) {
        this.idprogrammation = idprogrammation;
    }

    public Date getDateprevisionnel() {
        return dateprevisionnel;
    }

    public void setDateprevisionnel(Date dateprevisionnel) {
        this.dateprevisionnel = dateprevisionnel;
    }

    public Date getDaterealisation() {
        return daterealisation;
    }

    public void setDaterealisation(Date daterealisation) {
        this.daterealisation = daterealisation;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public Boolean getEnvoye() {
        return envoye;
    }

    public void setEnvoye(Boolean envoye) {
        this.envoye = envoye;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getTypefichier() {
        return typefichier;
    }

    public void setTypefichier(String typefichier) {
        this.typefichier = typefichier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Integer getRetard() {
        return retard;
    }

    public void setRetard(Integer retard) {
        this.retard = retard;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservationarchivee() {
        return observationarchivee;
    }

    public void setObservationarchivee(String observationarchivee) {
        this.observationarchivee = observationarchivee;
    }

    public Boolean getObservee() {
        return observee;
    }

    public void setObservee(Boolean observee) {
        this.observee = observee;
    }

    public Date getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public Boolean getObservationvalidee() {
        return observationvalidee;
    }

    public void setObservationvalidee(Boolean observationvalidee) {
        this.observationvalidee = observationvalidee;
    }

    public Integer getConteur() {
        return conteur;
    }

    public void setConteur(Integer conteur) {
        this.conteur = conteur;
    }

    public String getObservationutilisateur() {
        return observationutilisateur;
    }

    public void setObservationutilisateur(String observationutilisateur) {
        this.observationutilisateur = observationutilisateur;
    }

    public Acteur getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(Acteur idacteur) {
        this.idacteur = idacteur;
    }

    public Document getIddocument() {
        return iddocument;
    }

    public void setIddocument(Document iddocument) {
        this.iddocument = iddocument;
    }

    public Etapeprojet getIdetapeprojet() {
        return idetapeprojet;
    }

    public void setIdetapeprojet(Etapeprojet idetapeprojet) {
        this.idetapeprojet = idetapeprojet;
    }

    public Projetservice getIdprojetservice() {
        return idprojetservice;
    }

    public void setIdprojetservice(Projetservice idprojetservice) {
        this.idprojetservice = idprojetservice;
    }

    public Date getDateFinPrevisionnel() {
        return dateFinPrevisionnel;
    }

    public void setDateFinPrevisionnel(Date dateFinPrevisionnel) {
        this.dateFinPrevisionnel = dateFinPrevisionnel;
    }

    @XmlTransient
    public List<Piecejointes> getPiecejointesList() {
        return piecejointesList;
    }

    public void setPiecejointesList(List<Piecejointes> piecejointesList) {
        this.piecejointesList = piecejointesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprogrammation != null ? idprogrammation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programmation)) {
            return false;
        }
        Programmation other = (Programmation) object;
        if ((this.idprogrammation == null && other.idprogrammation != null) || (this.idprogrammation != null && !this.idprogrammation.equals(other.idprogrammation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Programmation[ idprogrammation=" + idprogrammation + " ]";
    }

}
