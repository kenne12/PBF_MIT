/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.RechargeSms;
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
public class RechargeSmsFacade extends AbstractFacade<RechargeSms> implements RechargeSmsFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RechargeSmsFacade() {
        super(RechargeSms.class);
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(r.idRechargeSms) FROM RechargeSms r");
        try {
            return ((int) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<RechargeSms> findAllRange() {
        return em.createQuery("SELECT r FROM RechargeSms r ORDER BY r.dateRecharge DESC")
                .getResultList();
    }

}
