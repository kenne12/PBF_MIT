/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Projet;
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
public class ProjetFacade extends AbstractFacade<Projet> implements ProjetFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjetFacade() {
        super(Projet.class);
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(p.idprojet) FROM Projet p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Projet> findAllRange() {
        Query query = this.em.createQuery("SELECT p FROM Projet p ORDER BY p.datecreation DESC");
        return query.getResultList();
    }

    @Override
    public List<Projet> findAllRange(boolean etat) {
        Query query = this.em.createQuery("SELECT p FROM Projet p WHERE p.etat=:etat ORDER BY p.datecreation DESC").setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<Projet> findAllRange(boolean etat, boolean cloturee) {
        Query query = this.em.createQuery("SELECT p FROM Projet p WHERE p.etat=:etat AND p.cloture=:cloture ORDER BY p.datecreation DESC").setParameter("etat", etat).setParameter("cloture", cloturee);
        return query.getResultList();
    }

}
