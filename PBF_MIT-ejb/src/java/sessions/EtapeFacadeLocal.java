/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Etape;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface EtapeFacadeLocal {

    void create(Etape etape);

    void edit(Etape etape);

    void remove(Etape etape);

    Etape find(Object id);

    List<Etape> findAll();

    List<Etape> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Etape> findAllRange() throws Exception;

}
