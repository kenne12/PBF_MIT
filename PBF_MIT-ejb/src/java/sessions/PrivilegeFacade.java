/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Privilege;
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
public class PrivilegeFacade extends AbstractFacade<Privilege> implements PrivilegeFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrivilegeFacade() {
        super(Privilege.class);
    }

    @Override
    public List<Privilege> findAll1() {
        List<Privilege> privileges = null;
        Query query = em.createQuery("SELECT p FROM Privilege p ORDER BY p.idutilisateur.nom, p.idutilisateur.prenom,p.idmenu.idmenu DESC");
        privileges = query.getResultList();
        return privileges;
    }

    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(p.idprivilege) FROM Privilege p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result += 1;
        }
        return result;
    }

    @Override
    public List<Privilege> findByUser(int utilisateur) {
        List<Privilege> privileges = null;
        Query query = em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur");
        query.setParameter("utilisateur", utilisateur);
        privileges = query.getResultList();
        return privileges;
    }

    @Override
    public Privilege findByUser(int utilisateur, int menu) {
        Privilege privilege = null;
        Query query = em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur AND p.idmenu.idmenu=:menu");
        query.setParameter("utilisateur", utilisateur).setParameter("menu", menu);
        if (!query.getResultList().isEmpty()) {
            privilege = (Privilege) query.getResultList().get(0);
        }
        return privilege;
    }

    @Override
    public void delete(int menu, int utilisateur) {
        try {
            Query query = em.createQuery("DELETE FROM Privilege p WHERE p.idmenu.idmenu=:menu AND p.idutilisateur.idutilisateur=:utilisateur");
            query.setParameter("menu", menu).setParameter("utilisateur", utilisateur);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByIdUtilisateur(int idUtilisateur) {
        Query query = em.createQuery("DELETE FROM Privilege p WHERE p.idutilisateur.idutilisateur=:idUtilisateur");
        query.setParameter("idUtilisateur", idUtilisateur);
        query.executeUpdate();
    }

}
