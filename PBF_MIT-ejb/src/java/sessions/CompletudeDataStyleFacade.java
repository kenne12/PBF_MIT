/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.CompletudeDataStyle;
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
public class CompletudeDataStyleFacade extends AbstractFacade<CompletudeDataStyle> implements CompletudeDataStyleFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompletudeDataStyleFacade() {
        super(CompletudeDataStyle.class);
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(c.id) FROM CompletudeDataStyle c");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<CompletudeDataStyle> findAllRange() {
        return em.createQuery("SELECT c FROM CompletudeDataStyle c ORDER BY c.borneInferieur , c.borneSuperieur")
                .getResultList();
    }

}
