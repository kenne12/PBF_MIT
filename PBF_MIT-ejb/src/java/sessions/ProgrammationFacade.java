/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Programmation;
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
public class ProgrammationFacade extends AbstractFacade<Programmation> implements ProgrammationFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgrammationFacade() {
        super(Programmation.class);
    }

    @Override
    public Long nextVal() {
        Query query = em.createQuery("SELECT MAX(p.idprogrammation) FROM Programmation p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Programmation> findByIdprojet(int idprojet) throws Exception {
        Query query = em.createQuery("SELECT p FROM Programmation p WHERE p.idetapeprojet.idprojet.idprojet=:idprojet");
        query.setParameter("idprojet", idprojet);
        return query.getResultList();
    }

    @Override
    public List<Programmation> findByIdprojetIdservice(long idprojetservice) throws Exception {
        Query query = em.createQuery("SELECT p FROM Programmation p WHERE p.idprojetservice.idprojetservice=:idprojetservice ORDER BY p.idetapeprojet.numero");
        query.setParameter("idprojetservice", idprojetservice);
        return query.getResultList();
    }

    @Override
    public Programmation findByIdprojetIdservice(long idprojetservice, int numero) throws Exception {
        Query query = em.createQuery("SELECT p FROM Programmation p WHERE p.idprojetservice.idprojetservice=:idprojetservice AND p.idetapeprojet.numero=:numero");
        query.setParameter("idprojetservice", idprojetservice).setParameter("numero", numero);
        List<Programmation> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Programmation> findByIdprojetservice(long idprojetservice) throws Exception {
        Query query = em.createQuery("SELECT p FROM Programmation p WHERE p.idprojetservice.idprojetservice=:idprojetservice ORDER BY p.idetapeprojet.numero");
        query.setParameter("idprojetservice", idprojetservice);
        return query.getResultList();
    }

    @Override
    public void deleteByIdprojetIdservice(long idprojetservice) throws Exception {
        Query query = em.createQuery("DELETE FROM Programmation p WHERE p.idprojetservice.idprojetservice=:idprojetservice");
        query.setParameter("idprojetservice", idprojetservice);
        query.executeUpdate();
    }

    @Override
    public void deleteByIdprojet(int idprojet) throws Exception {
        Query query = em.createQuery("DELETE FROM Programmation p WHERE p.idprojetservice.idprojet.idprojet=:idprojet");
        query.setParameter("idprojet", idprojet);
        query.executeUpdate();
    }

    @Override
    public Object[] getRetardByIdService(long idProjetService) {
        em.createQuery("SELECT p FROM Programmation p", Programmation[].class);

        return em.createQuery("SELECT p.idprojetservice,SUM(p.retard) FROM Programmation p GROUP BY p.idprojetservice", Object[].class)
                .setParameter("idProjetService", idProjetService)
                .getSingleResult();
    }

}
