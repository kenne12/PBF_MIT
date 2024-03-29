/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Service;
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
public class ServiceFacade extends AbstractFacade<Service> implements ServiceFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceFacade() {
        super(Service.class);
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(s.idservice) FROM Service s");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Service> findParentService() throws Exception {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0 ORDER BY s.nom")
                .getResultList();
    }

    @Override
    public List<Service> findAllRange() throws Exception {
        return this.em.createQuery("SELECT s FROM Service s ORDER BY s.idparent, s.nom")
                .getResultList();
    }

    @Override
    public List<Service> findAllRange(boolean central) {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.central=:central ORDER BY s.nom")
                .setParameter("central", central)
                .getResultList();
    }

    @Override
    public List<Service> findAllRangeParent() {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0 ORDER BY s.nom")
                .getResultList();
    }

    @Override
    public List<Service> findByServiceParent(int idparent) {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent!=0 AND s.idparent=:idparent ORDER BY s.nom")
                .setParameter("idparent", idparent)
                .getResultList();
    }

    @Override
    public List<Service> findByServiceWithoutAcv(int idparent) {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent!=0 AND s.idparent=:idparent AND s.regional=false ORDER BY s.nom")
                .setParameter("idparent", idparent)
                .getResultList();
    }

    @Override
    public List<Service> findByServiceParent(int idparent, boolean central, boolean regional) {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent!=0 AND s.idparent=:idparent AND s.central=:central AND s.regional=:regional ORDER BY s.nom")
                .setParameter("idparent", idparent).setParameter("central", central).setParameter("regional", regional)
                .getResultList();
    }

    @Override
    public List<Service> findAllCentralAndRegional() {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.central=:central OR s.regional=:regional ORDER BY s.nom")
                .setParameter("central", true).setParameter("regional", true)
                .getResultList();
    }

    @Override
    public List<Service> findAllRangeParentOrCtn() {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0 OR s.central=true ORDER BY s.nom")
                .getResultList();
    }

    @Override
    public List<Service> findAllRangeParentWithoutAllCentral() {
        return this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0 AND s.central=false ORDER BY s.nom")
                .getResultList();
    }

    @Override
    public Service findByServiceParentAndRegion(int idparent, boolean regional) {
        List list = this.em.createQuery("SELECT s FROM Service s WHERE s.idparent!=0 AND s.idparent=:idparent AND s.regional=:regional ORDER BY s.nom")
                .setParameter("idparent", idparent).setParameter("regional", regional)
                .getResultList();

        if (!list.isEmpty()) {
            return (Service) list.get(0);
        }
        return null;
    }

    @Override
    public Service findAcvByRegion(int idService) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.idparent=:idService AND s.regional = true")
                .setParameter("idService", idService);
        try {
            return (Service) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
