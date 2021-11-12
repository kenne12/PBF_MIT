package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification_acteur")
public class NotificationActeur implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected NotificationActeurPK notificationActeurPK;
    
    @JoinColumn(name = "idnotification", referencedColumnName = "idnotification", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Notification notification;
    @JoinColumn(name = "idacteur", referencedColumnName = "idacteur", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Acteur acteur;
    
    public NotificationActeur() {
    }
    
    public NotificationActeur(NotificationActeurPK notificationActeurPK) {
        this.notificationActeurPK = notificationActeurPK;
    }
    
    public NotificationActeur(int idNotification, int idActeur) {
        this.notificationActeurPK = new NotificationActeurPK(idNotification, idActeur);
    }
    
    public NotificationActeurPK getNotificationActeurPK() {
        return notificationActeurPK;
    }
    
    public void setNotificationActeurPK(NotificationActeurPK notificationActeurPK) {
        this.notificationActeurPK = notificationActeurPK;
    }
    
    public Notification getNotification() {
        return notification;
    }
    
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
    public Acteur getActeur() {
        return acteur;
    }
    
    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.notificationActeurPK);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificationActeur other = (NotificationActeur) obj;
        return Objects.equals(this.notificationActeurPK, other.notificationActeurPK);
    }
    
}
