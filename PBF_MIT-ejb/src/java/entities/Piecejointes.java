/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Piecejointes.findAll", query = "SELECT p FROM Piecejointes p"),
    @NamedQuery(name = "Piecejointes.findByIdpiecejointes", query = "SELECT p FROM Piecejointes p WHERE p.idpiecejointes = :idpiecejointes"),
    @NamedQuery(name = "Piecejointes.findByDatevalidation", query = "SELECT p FROM Piecejointes p WHERE p.datevalidation = :datevalidation"),
    @NamedQuery(name = "Piecejointes.findByTexte", query = "SELECT p FROM Piecejointes p WHERE p.texte = :texte"),
    @NamedQuery(name = "Piecejointes.findByObservation", query = "SELECT p FROM Piecejointes p WHERE p.observation = :observation"),
    @NamedQuery(name = "Piecejointes.findByValidee", query = "SELECT p FROM Piecejointes p WHERE p.validee = :validee"),
    @NamedQuery(name = "Piecejointes.findByChemin", query = "SELECT p FROM Piecejointes p WHERE p.chemin = :chemin"),
    @NamedQuery(name = "Piecejointes.findByTypefichier", query = "SELECT p FROM Piecejointes p WHERE p.typefichier = :typefichier"),
    @NamedQuery(name = "Piecejointes.findByVu", query = "SELECT p FROM Piecejointes p WHERE p.vu = :vu")})
public class Piecejointes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idpiecejointes;
    @Temporal(TemporalType.DATE)
    private Date datevalidation;
    @Size(max = 254)
    private String texte;
    @Size(max = 254)
    private String observation;
    private Boolean validee;
    @Size(max = 254)
    private String chemin;
    @Size(max = 2147483647)
    private String typefichier;
    private Boolean vu;
    @JoinColumn(name = "idprogrammation", referencedColumnName = "idprogrammation")
    @ManyToOne(fetch = FetchType.LAZY)
    private Programmation idprogrammation;
    @JoinColumn(name = "idtraitement", referencedColumnName = "idtraitement")
    @ManyToOne(fetch = FetchType.LAZY)
    private Traitement idtraitement;

    public Piecejointes() {
    }

    public Piecejointes(Long idpiecejointes) {
        this.idpiecejointes = idpiecejointes;
    }

    public Long getIdpiecejointes() {
        return idpiecejointes;
    }

    public void setIdpiecejointes(Long idpiecejointes) {
        this.idpiecejointes = idpiecejointes;
    }

    public Date getDatevalidation() {
        return datevalidation;
    }

    public void setDatevalidation(Date datevalidation) {
        this.datevalidation = datevalidation;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean getValidee() {
        return validee;
    }

    public void setValidee(Boolean validee) {
        this.validee = validee;
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

    public Boolean getVu() {
        return vu;
    }

    public void setVu(Boolean vu) {
        this.vu = vu;
    }

    public Programmation getIdprogrammation() {
        return idprogrammation;
    }

    public void setIdprogrammation(Programmation idprogrammation) {
        this.idprogrammation = idprogrammation;
    }

    public Traitement getIdtraitement() {
        return idtraitement;
    }

    public void setIdtraitement(Traitement idtraitement) {
        this.idtraitement = idtraitement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpiecejointes != null ? idpiecejointes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Piecejointes)) {
            return false;
        }
        Piecejointes other = (Piecejointes) object;
        if ((this.idpiecejointes == null && other.idpiecejointes != null) || (this.idpiecejointes != null && !this.idpiecejointes.equals(other.idpiecejointes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Piecejointes[ idpiecejointes=" + idpiecejointes + " ]";
    }
    
}
