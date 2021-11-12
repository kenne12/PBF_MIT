/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Service;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ServiceFacadeLocal {

    void create(Service service);

    void edit(Service service);

    void remove(Service service);

    Service find(Object id);

    List<Service> findAll();

    List<Service> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Service> findParentService() throws Exception;

    List<Service> findAllRange() throws Exception;

    List<Service> findAllRange(boolean central);

    List<Service> findAllRangeParent();

    List<Service> findByServiceParent(int idparent);

    List<Service> findByServiceWithoutAcv(int idparent);

    List<Service> findByServiceParent(int idparent, boolean central, boolean regional);

    List<Service> findAllCentralAndRegional();

    List<Service> findAllRangeParentOrCtn();

    List<Service> findAllRangeParentWithoutAllCentral();

    Service findByServiceParentAndRegion(int idparent, boolean regional);

    Service findAcvByRegion(int idService);

}
