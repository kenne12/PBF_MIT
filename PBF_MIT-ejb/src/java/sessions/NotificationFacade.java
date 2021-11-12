/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notification;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@Stateless
public class NotificationFacade extends AbstractFacade<Notification> implements NotificationFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificationFacade() {
        super(Notification.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(n.idnotification) FROM Notification n");
        try {
            Integer result = (Integer) query.getSingleResult();
            return result == null ? 1 : (result + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<Notification> findAllByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin) {
        
        //em.createQuery("SELECT n FROM Notification n ", null)
        
        return em.createQuery("SELECT n FROM Notification n WHERE n.acteurs.idservice.idservice=:idService AND n.dateEnvoi BETWEEN :dateDebut AND :dateFin")
                .setParameter("idService", idService)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }
    
    
    @Override
    public List<Notification> findAllSmsByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin) {
        return em.createQuery("SELECT n FROM Notification n WHERE n.acteurs.idservice.idservice=:idService AND n.dateEnvoi BETWEEN :dateDebut AND :dateFin AND n.sms = true")
                .setParameter("idService", idService)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }
    
    @Override
    public List<Notification> findAllMailByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin) {
        return em.createQuery("SELECT n FROM Notification n WHERE n.acteurs.idservice.idservice=:idService AND n.dateEnvoi BETWEEN :dateDebut AND :dateFin AND n.mail = true")
                .setParameter("idService", idService)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

}
