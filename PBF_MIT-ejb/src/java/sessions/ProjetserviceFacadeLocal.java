/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Projetservice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ProjetserviceFacadeLocal {

    void create(Projetservice projetservice);

    void edit(Projetservice projetservice);

    void remove(Projetservice projetservice);

    Projetservice find(Object id);

    List<Projetservice> findAll();

    List<Projetservice> findRange(int[] range);

    int count();

    Long nextVal();

    List<Projetservice> findByIdprojet(int idprojet);

    List<Projetservice> findByIdprojet(int idprojet, boolean struct_central);

    List<Projetservice> findByIdprojetRegional(int idprojet, boolean struct_central, boolean regional);

    List<Projetservice> findByIdprojetRegional(int idprojet, boolean struct_central, boolean regional, boolean visibiliteSuivi, boolean cloture);

    List<Projetservice> findAll(boolean etat, boolean struct_central);

    List<Projetservice> findByIdservice(int idservice);

    List<Projetservice> findByIdservice(int idservice, boolean etat, boolean cloturee);

    List<Projetservice> findByIdservice(int idservice, int idprojet);

    List<Projetservice> findByIdserviceparent(int idserviceParent, int idprojet);

    List<Projetservice> findByIdserviceparentVs(int idserviceParent, int idprojet, boolean visibiliteSuivi, boolean cloture);

    List<Projetservice> findByIdserviceparent(int idserviceParent, boolean etat, boolean cloturee) throws Exception;

    void deleteByIdprojet(int idprojet) throws Exception;

    void deleteByIdprojetService(long idprojetService) throws Exception;

}
