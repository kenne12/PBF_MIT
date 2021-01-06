/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Etapeprojet;
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
public class EtapeprojetFacade extends AbstractFacade<Etapeprojet> implements EtapeprojetFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapeprojetFacade() {
        super(Etapeprojet.class);
    }

    @Override
    public Long nextVal() {
        Query query = em.createQuery("SELECT MAX(e.idetapeprojet) FROM Etapeprojet e");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Etapeprojet> findByIdprojet(int idprojet) {
        Query query = em.createQuery("SELECT e FROM Etapeprojet e WHERE e.idprojet.idprojet=:idprojet ORDER BY e.numero");
        query.setParameter("idprojet", idprojet);
        return query.getResultList();
    }

    @Override
    public void deleteByIdprojet(int idprojet) throws Exception {
        Query query = em.createQuery("DELETE FROM Etapeprojet e WHERE e.idprojet.idprojet=:idprojet");
        query.setParameter("idprojet", idprojet);
        query.executeUpdate();
    }

}
