/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Periode;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface PeriodeFacadeLocal {

    void create(Periode periode);

    void edit(Periode periode);

    void remove(Periode periode);

    Periode find(Object id);

    List<Periode> findAll();

    List<Periode> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Periode> findParentPeriod();

    List<Periode> findAllRange();

    List<Periode> findByIdParent(int idParent);
    
    
    Periode findParentPeriodDefault();

}
