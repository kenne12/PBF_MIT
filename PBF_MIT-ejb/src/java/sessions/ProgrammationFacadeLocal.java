/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Programmation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ProgrammationFacadeLocal {

    void create(Programmation programmation);

    void edit(Programmation programmation);

    void remove(Programmation programmation);

    Programmation find(Object id);

    List<Programmation> findAll();

    List<Programmation> findRange(int[] range);

    int count();

    Long nextVal();

    List<Programmation> findByIdprojet(int idprojet) throws Exception;

    List<Programmation> findByIdprojetIdservice(long idprojetservice) throws Exception;

    Programmation findByIdprojetIdservice(long idprojetservice, int numero) throws Exception;

    List<Programmation> findByIdprojetservice(long idprojetservice) throws Exception;

    void deleteByIdprojetIdservice(long idprojetservice) throws Exception;

    void deleteByIdprojet(int idprojet) throws Exception;

    double getRetardByIdService(long idProjetService);

    double getRetardByIdEtapeProjet(long idEtapeProjet);

    double getRetardByIdprojet(int idProjet);

    Double getRetardByIdServiceIdPeriode(long idService, int idPeriode);

    double getRetardByIdServiceIdPeriodeParent(long idService, int idPeriode);

    double getRetardByIdServiceParentIdperiode(long idService, int idPeriode);

    double getRetardByIdServiceParentIdperiodeParent(long idService, int idPeriode);

    Double getRetardByIdServiceParentIdPeriode(long idService, int idPeriode);

    double getRetardByIdServiceParentIdPeriodeParent(long idService, int idPeriode);

    /*
     *
     @Comment Section Promptitude
     @Param int idEtape
     @Param int idPeriode
     */
    Double getRetardByIdEtapeIdPeriode(int idEtape, int idPeriode);

    double getRetardByIdEtapeIdPeriodeParent(int idEtape, int idPeriode);

    Double getRetardByIdPeriode(int idPeriode);

    Double getRetardByIdPeriodeParent(int idPeriode);

    /*
     *Promptitude niveau region
     *
     */
    Double getRetardByIdEtapeIdPeriodeIdserviceParent(int idEtape, int idPeriode, long idServiceParent);

    Double getRetardByIdEtapeIdPeriodeParentIdserviceParent(int idEtape, int idPeriodeParent, long idServiceParent);

    Double getRetardByIdPeriodeIdEtape(int idPeriode, int idEtape);

    Double getRetardByIdPeriodeParentIdEtape(int idPeriodeParent, int idEtape);

    /**
     * Promptitude niveau acteur region
     *
     * @param idEtape,
     * @param idPeriode,
     * @param idActeur
     * @return Double
     */
    Double getRetardByIdEtapeIdPeriodeIdActeur(int idEtape, int idPeriode, int idActeur);

    Double getRetardByIdEtapeIdPeriodeParentIdActeur(int idEtape, int idPeriodeParent, int idActeur);

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return Double
     */
    Double getRetardByIdEtapeIdPeriodeParentIdservice(int idEtape, int idPeriodeParent, long idService);

    /**
     * @param idEtape
     * @param idPeriode
     * @param idService
     * @return Double
     */
    Double getRetardByIdEtapeIdPeriodeIdservice(int idEtape, int idPeriode, long idService);

    //section completude
    /**
     * @param idEtape
     * @param idPeriode
     * @return long
     */
    int getCompletudeByIdEtapeIdPeriode(int idEtape, int idPeriode);

    int getCompletudeByIdEtapeIdPeriodeValidees(int idEtape, int idPeriode);

    /**
     * @param idEtape
     * @param idPeriode
     * @return long
     */
    public int getCompletudeByIdEtapeIdPeriodeParent(int idEtape, int idPeriode);

    /**
     * @param idEtape,
     * @param idPeriode
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentValidees(int idEtape, int idPeriode);

    /**
     * @param idPeriode
     * @return int
     */
    public int getCompletudeByIdPeriode(int idPeriode);

    /**
     * @param idPeriode
     * @return int
     */
    public int getCompletudeByIdPeriodeValidees(int idPeriode);

    /**
     * @param idPeriode
     * @return int
     */
    public int getCompletudeByIdPeriodeParent(int idPeriode);

    /**
     * @param idPeriode
     * @return int
     */
    public int getCompletudeByIdPeriodeParentValidees(int idPeriode);

    /**
     * Complétude niveau region
     *
     * @param idEtape
     * @param idPeriode
     * @param idServiceParent
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdserviceParent(int idEtape, int idPeriode, long idServiceParent);

    /**
     * @param idEtape
     * @param idPeriode
     * @param idServiceParent
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(int idEtape, int idPeriode, long idServiceParent);

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdservice(int idEtape, int idPeriodeParent, long idService);

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idService
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(int idEtape, int idPeriodeParent, long idService);

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idServiceParent
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(int idEtape, int idPeriodeParent, long idServiceParent);

    /**
     * @param idEtape
     * @param idPeriodeParent
     * @param idServiceParent
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(int idEtape, int idPeriodeParent, long idServiceParent);

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeIdEtape(int idPeriode, int idEtape);

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeIdEtapeValidees(int idPeriode, int idEtape);

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeParentIdEtape(int idPeriodeParent, int idEtape);

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeParentIdEtapeValidees(int idPeriodeParent, int idEtape);

    /**
     * Complétude par acteur
     *
     * @param idEtape,
     * @param idPeriode,
     * @param idActeur,
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdActeur(int idEtape, int idPeriode, int idActeur);

    /**
     * @param idEtape,
     * @param idPeriode,
     * @param idActeur
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdActeurValidees(int idEtape, int idPeriode, int idActeur);

    /**
     * Complétude par acteur
     *
     * @param idEtape,
     * @param idPeriodeParent,
     * @param idActeur
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdActeur(int idEtape, int idPeriodeParent, int idActeur);

    /**
     * @param idEtape,
     * @param idPeriodeParent,
     * @param idActeur
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeParentIdActeurValidees(int idEtape, int idPeriodeParent, int idActeur);

    /**
     * Complétude par acteur
     *
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeIdEtapeSize(int idPeriode, int idEtape);

    /**
     * @param idPeriode
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeIdEtapeSizeValidees(int idPeriode, int idEtape);

    /**
     * Complétude par acteur
     *
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeParentIdEtapeSize(int idPeriodeParent, int idEtape);

    /**
     * @param idPeriodeParent
     * @param idEtape
     * @return int
     */
    public int getCompletudeByIdPeriodeParentIdEtapeSizeValidees(int idPeriodeParent, int idEtape);

    /**
     * Complétude niveau district
     *
     * @param idEtape
     * @param idPeriode
     * @param idService
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdservice(int idEtape, int idPeriode, long idService);

    /**
     * @param idEtape
     * @param idPeriode
     * @param idService
     * @return int
     */
    public int getCompletudeByIdEtapeIdPeriodeIdserviceValidees(int idEtape, int idPeriode, long idService);

    List<Programmation> findByIdetapeParentIdActeurIdetape(int idPeriode, int idActeur , int idEtape);

}
