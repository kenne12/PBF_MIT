/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notification;
import entities.NotificationActeur;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class NotificationActeurFacade extends AbstractFacade<NotificationActeur> implements NotificationActeurFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificationActeurFacade() {
        super(NotificationActeur.class);
    }

    @Override
    public List<Notification> findAllByIdperiodeParentCentralOrgUnit(int idService, int idPeriode) {

        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idparent=:idPeriode")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllSmsByIdperiodeParentCentralOrgUnit(int idService, int idPeriode) {
        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idparent=:idPeriode AND n.notification.sms = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllMailByIdperiodeParentCentralOrgUnit(int idService, int idPeriode) {
        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idparent=:idPeriode AND n.notification.mail = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllByIdperiodeParentRegionOrgUnit(int idService, int idPeriode) {
        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idparent=:idPeriode")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllSmsByIdperiodeParentRegionOrgUnit(int idService, int idPeriode) {
        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idparent=:idPeriode AND n.notification.sms = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllMailByIdperiodeParentRegionOrgUnit(int idService, int idPeriode) {
        return em.createQuery("SELECT DISTINCT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idparent=:idPeriode AND n.notification.mail = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode) {

        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idperiode=:idPeriode")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllSmsByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode) {
        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idperiode =:idPeriode AND n.notification.sms = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllMailByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode) {
        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idservice=:idService AND n.notification.idperiode.idperiode=:idPeriode AND n.notification.mail = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode) {
        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idperiode=:idPeriode")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

    @Override
    public List<Notification> findAllSmsByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode) {
        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idperiode=:idPeriode AND n.notification.sms = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }
    
    @Override
    public List<Notification> findAllMailByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode) {
        return em.createQuery("SELECT n.notification FROM NotificationActeur n WHERE n.acteur.idservice.idparent=:idService AND n.notification.idperiode.idperiode=:idPeriode AND n.notification.mail = true")
                .setParameter("idService", idService)
                .setParameter("idPeriode", idPeriode)
                .getResultList();
    }

}
