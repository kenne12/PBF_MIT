/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Utilisateur;
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
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    @Override
    public int nextVal() {
        Query query = em.createQuery("SELECT MAX(u.idutilisateur) FROM Utilisateur u");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result += 1;
        }
        return result;
    }

    @Override
    public Utilisateur login(String login, String password) throws Exception {
        Query query = em.createQuery("SELECT u FROM Utilisateur U WHERE u.login=:login AND u.password=:password");
        query.setParameter("login", login).setParameter("password", password);
        if (!query.getResultList().isEmpty()) {
            return (Utilisateur) query.getResultList().get(0);
        }
        return null;
    }

    @Override
    public List<Utilisateur> findByActif(Boolean actif) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.etat=:actif ORDER BY u.nom,u.prenom");
        query.setParameter("actif", actif);
        return query.getResultList();
    }

    @Override
    public List<Utilisateur> findByIdservice(int idservice) throws Exception {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.idacteur.idservice.idservice=:idservice");
        query.setParameter("idservice", idservice);
        return query.getResultList();
    }
    
    @Override
    public Utilisateur findByLogin(String login) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.login=:login");
        query.setParameter("login", login);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Utilisateur) list.get(0);
        }
        return null;
    }

}
