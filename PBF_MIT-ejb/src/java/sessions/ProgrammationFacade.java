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
            return -1d;
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
            return -1;
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
            return -1d;
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
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    /*
     *Promptitude niveau region
     @param idEtape
     @param idPeriode
     @param idServiceParent
     @return Long
     */
    @Override
    public Double getRetardByIdEtapeIdPeriodeIdserviceParent(int idEtape, int idPeriode, long idServiceParent) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idparent=:idServiceParent", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdEtapeIdPeriodeParentIdserviceParent(int idEtape, int idPeriodeParent, long idServiceParent) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idparent=:idServiceParent", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdPeriodeIdEtape(int idPeriode, int idEtape) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idPeriode", idPeriode).setParameter("idEtape", idEtape)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdPeriodeParentIdEtape(int idPeriodeParent, int idEtape) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idParentId", idPeriodeParent).setParameter("idEtape", idEtape)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    /*
     promptitude region acteur    
     */
    @Override
    public Double getRetardByIdEtapeIdPeriodeIdActeur(int idEtape, int idPeriode, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idacteur.idacteur=:idActeur", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idActeur", idActeur)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdEtapeIdPeriodeParentIdActeur(int idEtape, int idPeriodeParent, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idacteur.idacteur=:idActeur", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idActeur", idActeur)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return Double
     */
    @Override
    public Double getRetardByIdEtapeIdPeriodeParentIdservice(int idEtape, int idPeriodeParent, long idService) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idservice=:idService", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idService", idService)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    @Override
    public Double getRetardByIdEtapeIdPeriodeIdservice(int idEtape, int idPeriode, long idService) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idservice=:idService", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idService", idService)
                .getResultList();

        if (list.isEmpty()) {
            return -1d;
        }

        return list.stream()
                .map(o -> new Programmation((int) o[0]))
                .mapToInt(p -> p.getRetard())
                .average()
                .getAsDouble();
    }

    //section completude
    @Override
    public int getCompletudeByIdEtapeIdPeriode(int idEtape, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /*
     @param idEtape,
     @param idPeriode
     @retur int   
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeValidees(int idEtape, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /*
     @param idEtape,
     @param idPeriode
     @retur int   
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParent(int idEtape, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriode", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /*
     *@param idEtape,
     *@param idPeriode
     *@retur int   
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentValidees(int idEtape, int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriode AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /*
     @param idPeriode
     @retur Long   
     */
    @Override
    public int getCompletudeByIdPeriode(int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode", Object[].class)
                .setParameter("idPeriode", idPeriode).getResultList();
        return list.isEmpty() ? -1 : list.size();
    }

    /*
     * @param idPeriode
     * @return Long   
     */
    @Override
    public int getCompletudeByIdPeriodeValidees(int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.valide = true", Object[].class)
                .setParameter("idPeriode", idPeriode).getResultList();
        return list.isEmpty() ? 0 : list.size();
    }

    /*
     * @param idPeriode
     * @return int   
     */
    @Override
    public int getCompletudeByIdPeriodeParent(int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId", Object[].class)
                .setParameter("idParentId", idPeriode).getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /*
     * @param idPeriode
     * @return int   
     */
    @Override
    public int getCompletudeByIdPeriodeParentValidees(int idPeriode) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.valide = true", Object[].class)
                .setParameter("idParentId", idPeriode).getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /*
     Complétude niveau region
     @param idEtape
     @param idPeriode
     @param idServiceParent
     @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdserviceParent(int idEtape, int idPeriode, long idServiceParent) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idparent=:idServiceParent", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriode
     * @param idServiceParent
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(int idEtape, int idPeriode, long idServiceParent) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idparent=:idServiceParent AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdservice(int idEtape, int idPeriodeParent, long idService) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idservice=:idService", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idService", idService)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(int idEtape, int idPeriodeParent, long idService) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idservice=:idService AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idService", idService)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idServiceParent
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(int idEtape, int idPeriodeParent, long idServiceParent) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idparent=:idServiceParent", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idServiceParent
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(int idEtape, int idPeriodeParent, long idServiceParent) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idprojetservice.idservice.idparent=:idServiceParent AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idServiceParent", idServiceParent)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeIdEtape(int idPeriode, int idEtape) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idPeriode", idPeriode).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeIdEtapeValidees(int idPeriode, int idEtape) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idetapeprojet.idetape.idetape=:idEtape AND p.valide = true", Object[].class)
                .setParameter("idPeriode", idPeriode).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeParentIdEtape(int idPeriodeParent, int idEtape) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idParentId", idPeriodeParent).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeParentIdEtapeValidees(int idPeriodeParent, int idEtape) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.idetapeprojet.idetape.idetape=:idEtape AND p.valide = true", Object[].class)
                .setParameter("idParentId", idPeriodeParent).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * Complétude par acteur
     *
     * @param idEtape,
     * @param idPeriode,
     * @param idActeur
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdActeur(int idEtape, int idPeriode, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idacteur.idacteur=:idActeur", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idActeur", idActeur)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriode
     * @param idActeur
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdActeurValidees(int idEtape, int idPeriode, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idacteur.idacteur=:idActeur AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idActeur", idActeur)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * Complétude par acteur
     *
     * @param idEtape,
     * @param idPeriodeParent,
     * @param idActeur
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdActeur(int idEtape, int idPeriodeParent, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idacteur.idacteur=:idActeur", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idActeur", idActeur)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * Complétude par acteur
     *
     * @param idEtape,
     * @param idPeriodeParent,
     * @param idActeur
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeParentIdActeurValidees(int idEtape, int idPeriodeParent, int idActeur) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idparent=:idPeriodeParent AND p.idacteur.idacteur=:idActeur AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriodeParent", idPeriodeParent).setParameter("idActeur", idActeur)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idPeriode
     * @param idEtape
     */
    @Override
    public int getCompletudeByIdPeriodeIdEtapeSize(int idPeriode, int idEtape) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idPeriode", idPeriode).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeIdEtapeSizeValidees(int idPeriode, int idEtape) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idetapeprojet.idetape.idetape=:idEtape AND p.valide = true", Object[].class)
                .setParameter("idPeriode", idPeriode).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * @param idPeriodeParent
     * @param idEtape
     */
    @Override
    public int getCompletudeByIdPeriodeParentIdEtapeSize(int idPeriodeParent, int idEtape) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idParentId", idPeriodeParent).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    @Override
    public int getCompletudeByIdPeriodeParentIdEtapeSizeValidees(int idPeriodeParent, int idEtape) {

        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idprojet.idperiode.idparent=:idParentId AND p.idetapeprojet.idetape.idetape=:idEtape", Object[].class)
                .setParameter("idParentId", idPeriodeParent).setParameter("idEtape", idEtape)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

    /**
     * Complétude niveau district
     *
     * @param idEtape
     * @param idPeriode
     * @param idService
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdservice(int idEtape, int idPeriode, long idService) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idservice=:idService", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idService", idService)
                .getResultList();

        return list.isEmpty() ? -1 : list.size();
    }

    /**
     * @param idEtape
     * @param idPeriode
     * @param idService
     * @return int
     */
    @Override
    public int getCompletudeByIdEtapeIdPeriodeIdserviceValidees(int idEtape, int idPeriode, long idService) {
        List<Object[]> list = em.createQuery("SELECT p.retard, p.idacteur.idacteur FROM Programmation p WHERE p.idetapeprojet.idetape.idetape=:idEtape AND p.idetapeprojet.idprojet.idperiode.idperiode=:idPeriode AND p.idprojetservice.idservice.idservice=:idService AND p.valide = true", Object[].class)
                .setParameter("idEtape", idEtape).setParameter("idPeriode", idPeriode).setParameter("idService", idService)
                .getResultList();

        return list.isEmpty() ? 0 : list.size();
    }

}
