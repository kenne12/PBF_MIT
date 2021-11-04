/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Parametrage.findAll", query = "SELECT p FROM Parametrage p"),
    @NamedQuery(name = "Parametrage.findByIdparametrage", query = "SELECT p FROM Parametrage p WHERE p.idparametrage = :idparametrage"),
    @NamedQuery(name = "Parametrage.findByRepertoireDocument", query = "SELECT p FROM Parametrage p WHERE p.repertoireDocument = :repertoireDocument"),
    @NamedQuery(name = "Parametrage.findByRepertoirePiece", query = "SELECT p FROM Parametrage p WHERE p.repertoirePiece = :repertoirePiece")})
public class Parametrage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idparametrage;
    @Size(max = 2147483647)
    @Column(name = "repertoire_document")
    private String repertoireDocument;
    @Size(max = 2147483647)
    @Column(name = "repertoire_piece")
    private String repertoirePiece;
    private String repertoire;

    @Column(name = "allmysms_api_url")
    private String allmysmsApiUrl;

    @Column(name = "orange_auth_header")
    private String orangeAuthHeader;

    public Parametrage() {
    }

    public Parametrage(Integer idparametrage) {
        this.idparametrage = idparametrage;
    }

    public Integer getIdparametrage() {
        return idparametrage;
    }

    public void setIdparametrage(Integer idparametrage) {
        this.idparametrage = idparametrage;
    }

    public String getRepertoireDocument() {
        return repertoireDocument;
    }

    public void setRepertoireDocument(String repertoireDocument) {
        this.repertoireDocument = repertoireDocument;
    }

    public String getRepertoirePiece() {
        return repertoirePiece;
    }

    public void setRepertoirePiece(String repertoirePiece) {
        this.repertoirePiece = repertoirePiece;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getAllmysmsApiUrl() {
        return allmysmsApiUrl;
    }

    public void setAllmysmsApiUrl(String allmysmsApiUrl) {
        this.allmysmsApiUrl = allmysmsApiUrl;
    }

    public String getOrangeAuthHeader() {
        return orangeAuthHeader;
    }

    public void setOrangeAuthHeader(String orangeAuthHeader) {
        this.orangeAuthHeader = orangeAuthHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametrage != null ? idparametrage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametrage)) {
            return false;
        }
        Parametrage other = (Parametrage) object;
        if ((this.idparametrage == null && other.idparametrage != null) || (this.idparametrage != null && !this.idparametrage.equals(other.idparametrage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Parametrage[ idparametrage=" + idparametrage + " ]";
    }

}
