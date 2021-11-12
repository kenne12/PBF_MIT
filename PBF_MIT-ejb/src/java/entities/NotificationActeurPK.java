package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class NotificationActeurPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    private int idnotification;

    @Basic(optional = false)
    @NotNull
    private int idacteur;

    public NotificationActeurPK() {
    }

    public NotificationActeurPK(int idnotification, int idacteur) {
        this.idnotification = idnotification;
        this.idacteur = idacteur;
    }

    public int getIdnotification() {
        return idnotification;
    }

    public void setIdnotification(int idnotification) {
        this.idnotification = idnotification;
    }

    public int getIdacteur() {
        return idacteur;
    }

    public void setIdacteur(int idacteur) {
        this.idacteur = idacteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) this.idnotification;
        hash += this.idacteur;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NotificationActeurPK)) {
            return false;
        }
        NotificationActeurPK other = (NotificationActeurPK) object;
        if (this.idnotification != other.idnotification) {
            return false;
        }
        if (this.idacteur != other.idacteur) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificationActeurPK[ idnotification=" + this.idnotification + ", idacteur=" + this.idacteur + " ]";
    }
}
