/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Modealerte;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ModealerteFacadeLocal {

    void create(Modealerte modealerte);

    void edit(Modealerte modealerte);

    void remove(Modealerte modealerte);

    Modealerte find(Object id);

    List<Modealerte> findAll();

    List<Modealerte> findRange(int[] range);

    int count();
    
}
