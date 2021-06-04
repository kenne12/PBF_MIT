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
    public double getRetardByIdService(long idProjetService) {
        return em.createQuery("SELECT p.retard , p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idprojetservice=:idProjetservice", Object[].class)
                .setParameter("idProjetservice", idProjetService)
                .getResultList()
                .stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public double getRetardByIdEtapeProjet(long idProjetEtape) {
        return em.createQuery("SELECT p.retard , p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetapeprojet=:idEtapeProjet", Object[].class)
                .setParameter("idEtapeProjet", idProjetEtape)
                .getResultList()
                .stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public double getRetardByIdprojet(int idProjet) {
        return em.createQuery("SELECT p.retard , p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idprojet =:idProjet", Object[].class)
                .setParameter("idProjet", idProjet)
                .getResultList()
                .stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdServiceIdPeriode(long idService, int idPeriode) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idservice=:idService AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idService", idService).setParameter("idPeriode", idPeriode).getResultList();

        if (list.isEmpty()) {
            return 0d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();

        /*return em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idservice=:idService AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
         .setParameter("idService", idService).setParameter("idPeriode", idPeriode)
         .getResultList()
         .stream()
         .map(o -> new Programmation((int) o[0]))
         .mapToInt(p -> p.getRetard())
         .average()
         .getAsDouble();*/
    }

    @Override
    public double getRetardByIdServiceIdPeriodeParent(long idService, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idservice=:idService AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriode", Object[].class)
                .setParameter("idService", idService).setParameter("idPeriode", idPeriode)
                .getResultList();

        if (list.isEmpty()) {
            return 0;
        }
        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public double getRetardByIdServiceParentIdperiode(long idService, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idparent=:idParent AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idParent", idService).setParameter("idPeriode", idPeriode)
                .getResultList();

        if (list.isEmpty()) {
            return 0;
        }
        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public double getRetardByIdServiceParentIdperiodeParent(long idService, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idparent=:idParent AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent", Object[].class)
                .setParameter("idParent", idService).setParameter("idPeriodeParent", idPeriode)
                .getResultList();

        if (list.isEmpty()) {
            return 0;
        }
        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdServiceParentIdPeriode(long idService, int idPeriode) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idparent=:idService AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idService", idService).setParameter("idPeriode", idPeriode).getResultList();

        if (list.isEmpty()) {
            return 0d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();

    }

    @Override
    public double getRetardByIdServiceParentIdPeriodeParent(long idService, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idprojetservice.idservice.idparent=:idService AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriode", Object[].class)
                .setParameter("idService", idService).setParameter("idPeriode", idPeriode)
                .getResultList();

        if (list.isEmpty()) {
            return 0;
        }
        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdEtapeIdPeriode(int idEtape, int idPeriode) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).getResultList();

        if (list.isEmpty()) {
            return 0d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();

    }

    @Override
    public double getRetardByIdEtapeIdPeriodeParent(int idEtape, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriode", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode)
                .getResultList();

        if (list.isEmpty()) {
            return 0;
        }
        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdPeriode(int idPeriode) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idPeriode", idPeriode).getResultList();

        if (list.isEmpty()) {
            return 0d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();

    }
    
    @Override
    public Double getRetardByIdPeriodeParent(int idPeriode) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId", Object[].class)
                .setParameter("idParentId", idPeriode).getResultList();

        if (list.isEmpty()) {
            return 0d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();

    }

}
