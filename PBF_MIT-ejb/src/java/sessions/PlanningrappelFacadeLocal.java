/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Planningrappel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface PlanningrappelFacadeLocal {

    void create(Planningrappel planningrappel);

    void edit(Planningrappel planningrappel);

    void remove(Planningrappel planningrappel);

    Planningrappel find(Object id);

    List<Planningrappel> findAll();

    List<Planningrappel> findRange(int[] range);

    int count();
    
}
