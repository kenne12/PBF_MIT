/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Periode;
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
public class PeriodeFacade extends AbstractFacade<Periode> implements PeriodeFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodeFacade() {
        super(Periode.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.idperiode) FROM Periode p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Periode> findParentPeriod() {
        return this.em.createQuery("SELECT p FROM Periode p WHERE p.idparent=0")
                .getResultList();
    }

    @Override
    public List<Periode> findAllRange() {
        return this.em.createQuery("SELECT p FROM Periode p ORDER BY p.niveau DESC, p.numero ASC")
                .getResultList();
    }

    @Override
    public List<Periode> findByIdParent(int idParent) {
        return this.em.createQuery("SELECT p FROM Periode p WHERE p.idparent=:idParent ORDER BY p.numero")
                .setParameter("idParent", idParent)
                .getResultList();
    }

    @Override
    public Periode findParentPeriodDefault() {
        Query query = em.createQuery("SELECT p FROM Periode p WHERE p.idparent=0 AND p.defaultPeriod = true");
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Periode) list.get(0);
        }
        return null;
    }

}
