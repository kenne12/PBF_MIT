/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Acteur;
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
public class ActeurFacade extends AbstractFacade<Acteur> implements ActeurFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActeurFacade() {
        super(Acteur.class);
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(a.idacteur) FROM Acteur a");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Acteur> findAllRange() {
        Query query = em.createQuery("SELECT a FROM Acteur a ORDER BY a.nom,a.prenom");
        return query.getResultList();
    }

    @Override
    public List<Acteur> findAllRange(boolean struct_centrale) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.idservice.central=:s_central ORDER BY a.nom,a.prenom")
                .setParameter("s_central", struct_centrale)
                .getResultList();
    }

    @Override
    public List<Acteur> findByIdservice(int idservice) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.idservice.idservice=:idservice ORDER BY a.nom,a.prenom")
                .setParameter("idservice", idservice)
                .getResultList();
    }

    @Override
    public List<Acteur> findAllOrder() {
        return em.createQuery("SELECT a FROM Acteur a ORDER BY a.nom,a.prenom")
                .getResultList();
    }

    @Override
    public List<Acteur> findByIdserviceParent(int idParent) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.idservice.idparent=:idParent ORDER BY a.nom,a.prenom")
                .setParameter("idParent", idParent)
                .getResultList();
    }

    @Override
    public List<Acteur> findByIdserviceAndRegion(int idservice, boolean region) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.idservice.idservice=:idservice AND a.idservice.regional=:regional ORDER BY a.nom,a.prenom")
                .setParameter("idservice", idservice)
                .setParameter("regional", region)
                .getResultList();
    }

}
