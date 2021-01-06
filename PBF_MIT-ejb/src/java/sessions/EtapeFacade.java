/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Etape;
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
public class EtapeFacade extends AbstractFacade<Etape> implements EtapeFacadeLocal {
    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapeFacade() {
        super(Etape.class);
    }
    
    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(e.idetape) FROM Etape e");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }
    
    @Override
    public List<Etape> findAllRange() throws Exception {
        Query query = this.em.createQuery("SELECT e FROM Etape e ORDER BY e.code");
        return query.getResultList();
    }
    
}
