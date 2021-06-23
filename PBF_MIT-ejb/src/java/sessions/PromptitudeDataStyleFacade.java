/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.CompletudeDataStyle;
import entities.PromptitudeDataStyle;
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
public class PromptitudeDataStyleFacade extends AbstractFacade<PromptitudeDataStyle> implements PromptitudeDataStyleFacadeLocal {
    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PromptitudeDataStyleFacade() {
        super(PromptitudeDataStyle.class);
    }
    
    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(p.id) FROM PromptitudeDataStyle p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<PromptitudeDataStyle> findAllRange() {
        return em.createQuery("SELECT p FROM PromptitudeDataStyle p ORDER BY p.borneInferieur , p.borneSuperieur")
                .getResultList();
    }
    
}
