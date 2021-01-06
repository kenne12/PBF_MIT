/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Parametrage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ParametrageFacadeLocal {

    void create(Parametrage parametrage);

    void edit(Parametrage parametrage);

    void remove(Parametrage parametrage);

    Parametrage find(Object id);

    List<Parametrage> findAll();

    List<Parametrage> findRange(int[] range);

    int count();
    
}
