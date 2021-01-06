/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Piecejointes;
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
public class PiecejointesFacade extends AbstractFacade<Piecejointes> implements PiecejointesFacadeLocal {

    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PiecejointesFacade() {
        super(Piecejointes.class);
    }

    @Override
    public Long nextVal() {
        Query query = em.createQuery("SELECT MAX(p.idpiecejointes) FROM Piecejointes p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Piecejointes> findByIdprogrammation(long idprogrammation) throws Exception {
        Query query = em.createQuery("SELECT p FROM Piecejointes p WHERE p.idprogrammation.idprogrammation=:idprogrammation");
        query.setParameter("idprogrammation", idprogrammation);
        return query.getResultList();
    }

    @Override
    public List<Piecejointes> findByIdprojetservice(long idprojetservice) throws Exception {
        Query query = em.createQuery("SELECT p FROM Piecejointes p WHERE p.idprogrammation.idprojetservice=:idprojetservice");
        query.setParameter("idprojetservice", idprojetservice);
        return query.getResultList();
    }

    @Override
    public void deleteByIdprojetservice(long idprojetservice) throws Exception {
        Query query = em.createQuery("DELETE FROM Piecejointes p WHERE p.idprogrammation.idprojetservice.idprojetservice=:idprojetservice");
        query.setParameter("idprojetservice", idprojetservice);
        query.executeUpdate();
    }

    @Override
    public void deleteByIdprojet(int idprojet) throws Exception {
        Query query = em.createQuery("DELETE FROM Piecejointes p WHERE p.idprogrammation.idprojetservice.idprojet.idprojet=:idprojet");
        query.setParameter("idprojet", idprojet);
        query.executeUpdate();
    }

}
