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
        Query query = this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0");
        return query.getResultList();
    }

    @Override
    public List<Service> findAllRange() throws Exception {
        Query query = this.em.createQuery("SELECT s FROM Service s ORDER BY s.idparent, s.nom");
        return query.getResultList();
    }

    @Override
    public List<Service> findAllRange(boolean central) throws Exception {
        Query query = this.em.createQuery("SELECT s FROM Service s WHERE s.central=:central ORDER BY s.nom");
        query.setParameter("central", central);
        return query.getResultList();
    }

    @Override
    public List<Service> findAllRangeParent() throws Exception {
        Query query = this.em.createQuery("SELECT s FROM Service s WHERE s.idparent=0 ORDER BY s.nom");
        return query.getResultList();
    }

    @Override
    public List<Service> findByServiceParent(int idparent) throws Exception {
        Query query = this.em.createQuery("SELECT s FROM Service s  WHERE s.idparent!=0 AND s.idparent=:idparent ORDER BY s.nom");
        query.setParameter("idparent", idparent);
        return query.getResultList();
    }

}
