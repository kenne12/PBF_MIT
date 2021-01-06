/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Addresse;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface AddresseFacadeLocal {

    void create(Addresse addresse);

    void edit(Addresse addresse);

    void remove(Addresse addresse);

    Addresse find(Object id);

    List<Addresse> findAll();

    List<Addresse> findRange(int[] range);

    int count();

    Long nextVal();

}
