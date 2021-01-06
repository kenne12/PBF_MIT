/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Mouchard;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MouchardFacadeLocal {

    void create(Mouchard mouchard);

    void edit(Mouchard mouchard);

    void remove(Mouchard mouchard);

    Mouchard find(Object id);

    List<Mouchard> findAll();

    List<Mouchard> findRange(int[] range);

    int count();

    Long nextVal();

    void deleteByIdUtilisateur(int idUtilisateur);

}
