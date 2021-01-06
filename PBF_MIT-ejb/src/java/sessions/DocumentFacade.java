/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Document;
import entities.Etape;
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
public class DocumentFacade extends AbstractFacade<Document> implements DocumentFacadeLocal {
    @PersistenceContext(unitName = "PBF_MIT-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentFacade() {
        super(Document.class);
    }
    
    @Override
    public Integer nextVal() {
        Query query = em.createQuery("SELECT MAX(d.iddocument) FROM Document d");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }
    
    @Override
    public List<Document> findAllRange() throws Exception {
        Query query = this.em.createQuery("SELECT d FROM Document d ORDER BY d.code");
        return query.getResultList();
    }
    
}
