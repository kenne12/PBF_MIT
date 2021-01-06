/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Projetservice;
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
public class ProjetserviceFacade extends AbstractFacade<Projetservice> implements ProjetserviceFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjetserviceFacade() {
        super(Projetservice.class);
    }

    @Override
    public Long nextVal() {
        Query query = em.createQuery("SELECT MAX(p.idprojetservice) FROM Projetservice p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Projetservice> findByIdprojet(int idprojet) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idprojet.idprojet=:idprojet ORDER BY p.idservice.nom");
        query.setParameter("idprojet", idprojet);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdprojet(int idprojet, boolean struct_central) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idprojet.idprojet=:idprojet AND p.idservice.central=:central ORDER BY p.idservice.nom");
        query.setParameter("idprojet", idprojet).setParameter("central", struct_central);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdprojetRegional(int idprojet, boolean struct_central, boolean regional) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idprojet.idprojet=:idprojet AND p.idservice.central=:central AND p.idservice.regional=:regional ORDER BY p.idservice.nom");
        query.setParameter("idprojet", idprojet).setParameter("central", struct_central).setParameter("regional", regional);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdprojetRegional(int idprojet, boolean struct_central, boolean regional, boolean visibiliteSuivi, boolean cloture) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idprojet.idprojet=:idprojet AND p.idservice.central=:central AND p.idservice.regional=:regional AND p.idservice.visibilitesuivi=:vSuivi AND p.idprojet.cloture=:cloture ORDER BY p.idservice.nom");
        query.setParameter("idprojet", idprojet).setParameter("central", struct_central).setParameter("regional", regional).setParameter("vSuivi", visibiliteSuivi).setParameter("cloture", cloture);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findAll(boolean etat, boolean struct_central) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idprojet.etat=:etat AND p.idservice.central=:central ORDER BY p.idservice.nom");
        query.setParameter("etat", etat).setParameter("central", struct_central);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdservice(int idservice) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idservice=:idservice ORDER BY p.idprojet.nom");
        query.setParameter("idservice", idservice);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdservice(int idservice, boolean etat, boolean cloturee) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idservice=:idservice AND p.idprojet.etat=:etat AND p.idprojet.cloture=:cloture ORDER BY p.idprojet.nom");
        query.setParameter("idservice", idservice).setParameter("etat", etat).setParameter("cloture", cloturee);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdservice(int idservice, int idprojet) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idservice=:idservice AND p.idprojet.idprojet=:idprojet");
        query.setParameter("idservice", idservice).setParameter("idprojet", idprojet);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdserviceparent(int idserviceParent, int idprojet) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idparent=:idserviceParent AND p.idprojet.idprojet=:idprojet ORDER BY p.idprojet.nom");
        query.setParameter("idserviceParent", idserviceParent).setParameter("idprojet", idprojet);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdserviceparentVs(int idserviceParent, int idprojet, boolean visibiliteSuivi, boolean cloture) {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idparent=:idserviceParent AND p.idprojet.idprojet=:idprojet AND p.idservice.visibilitesuivi=:vSuivi AND p.idprojet.cloture=:cloture ORDER BY p.idprojet.nom");
        query.setParameter("idserviceParent", idserviceParent).setParameter("idprojet", idprojet).setParameter("vSuivi", visibiliteSuivi).setParameter("cloture", cloture);
        return query.getResultList();
    }

    @Override
    public List<Projetservice> findByIdserviceparent(int idserviceParent, boolean etat, boolean cloturee) throws Exception {
        Query query = em.createQuery("SELECT p FROM Projetservice p WHERE p.idservice.idparent=:idserviceParent AND p.idprojet.etat=:etat AND p.idprojet.cloture=:cloture ORDER BY p.idservice.nom");
        query.setParameter("idserviceParent", idserviceParent).setParameter("etat", etat).setParameter("cloture", cloturee);
        return query.getResultList();
    }

    @Override
    public void deleteByIdprojet(int idprojet) throws Exception {
        Query query = em.createQuery("DELETE FROM Projetservice s WHERE s.idprojet.idprojet=:idprojet");
        query.setParameter("idprojet", idprojet);
        query.executeUpdate();
    }
    
    @Override
    public void deleteByIdprojetService(long idprojetService) throws Exception {
        Query query = em.createQuery("DELETE FROM Projetservice s WHERE s.idprojetservice=:idprojetService");
        query.setParameter("idprojetService", idprojetService);
        query.executeUpdate();
    }

}
