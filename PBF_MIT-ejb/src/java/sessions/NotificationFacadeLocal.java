/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notification;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface NotificationFacadeLocal {

    void create(Notification notification);

    void edit(Notification notification);

    void remove(Notification notification);

    Notification find(Object id);

    List<Notification> findAll();

    List<Notification> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Notification> findAllByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin);

    List<Notification> findAllSmsByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin);

    List<Notification> findAllMailByIdperiodeParentCentralOrgUnit(int idService, Date dateDebut, Date dateFin);

}
