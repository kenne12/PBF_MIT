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

    /*
     *Promptitude niveau acteur region
     *
     */
    Double getRetardByIdEtapeIdPeriodeIdActeur(int idEtape, int idPeriode, int idActeur);

    Double getRetardByIdEtapeIdPeriodeParentIdActeur(int idEtape, int idPeriodeParent, int idActeur);

}
