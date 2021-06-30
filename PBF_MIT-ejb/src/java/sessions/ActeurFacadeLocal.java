/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Acteur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ActeurFacadeLocal {

    void create(Acteur acteur);

    void edit(Acteur acteur);

    void remove(Acteur acteur);

    Acteur find(Object id);

    List<Acteur> findAll();

    List<Acteur> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Acteur> findAllRange();

    List<Acteur> findAllRange(boolean struct_centrale);

    List<Acteur> findByIdservice(int idservice);

    List<Acteur> findAllOrder();

    List<Acteur> findByIdserviceParent(int idParent);

    List<Acteur> findByIdserviceAndRegion(int idservice, boolean region);

}
