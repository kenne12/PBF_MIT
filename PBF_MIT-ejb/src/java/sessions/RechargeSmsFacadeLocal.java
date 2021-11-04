/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.RechargeSms;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface RechargeSmsFacadeLocal {

    void create(RechargeSms rechargeSms);

    void edit(RechargeSms rechargeSms);

    void remove(RechargeSms rechargeSms);

    RechargeSms find(Object id);

    List<RechargeSms> findAll();

    List<RechargeSms> findRange(int[] range);

    int count();

    Integer nextVal();

    List<RechargeSms> findAllRange();

}
