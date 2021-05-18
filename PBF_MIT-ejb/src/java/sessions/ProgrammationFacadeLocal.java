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

}
