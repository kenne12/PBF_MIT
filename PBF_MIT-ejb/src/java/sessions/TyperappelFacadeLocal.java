/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Typerappel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface TyperappelFacadeLocal {

    void create(Typerappel typerappel);

    void edit(Typerappel typerappel);

    void remove(Typerappel typerappel);

    Typerappel find(Object id);

    List<Typerappel> findAll();

    List<Typerappel> findRange(int[] range);

    int count();
    
}
