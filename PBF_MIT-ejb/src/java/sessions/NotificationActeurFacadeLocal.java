/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notification;
import entities.NotificationActeur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface NotificationActeurFacadeLocal {

    void create(NotificationActeur notificationActeur);

    void edit(NotificationActeur notificationActeur);

    void remove(NotificationActeur notificationActeur);

    NotificationActeur find(Object id);

    List<NotificationActeur> findAll();

    List<NotificationActeur> findRange(int[] range);

    int count();

    List<Notification> findAllByIdperiodeParentCentralOrgUnit(int idService, int idPeriode);

    List<Notification> findAllSmsByIdperiodeParentCentralOrgUnit(int idService, int idPeriode);

    List<Notification> findAllMailByIdperiodeParentCentralOrgUnit(int idService, int idPeriode);

    List<Notification> findAllByIdperiodeParentRegionOrgUnit(int idService, int idPeriode);

    List<Notification> findAllSmsByIdperiodeParentRegionOrgUnit(int idService, int idPeriode);

    List<Notification> findAllMailByIdperiodeParentRegionOrgUnit(int idService, int idPeriode);

    List<Notification> findAllByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode);

    List<Notification> findAllSmsByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode);

    List<Notification> findAllMailByIdperiodeParentCentralOrgUnitGraphic(int idService, int idPeriode);

    List<Notification> findAllByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode);

    List<Notification> findAllSmsByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode);

    List<Notification> findAllMailByIdperiodeParentRegionOrgUnitGraphic(int idService, int idPeriode);

}
