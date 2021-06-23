/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.CompletudeDataStyle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface CompletudeDataStyleFacadeLocal {

    void create(CompletudeDataStyle completudeDataStyle);

    void edit(CompletudeDataStyle completudeDataStyle);

    void remove(CompletudeDataStyle completudeDataStyle);

    CompletudeDataStyle find(Object id);

    List<CompletudeDataStyle> findAll();

    List<CompletudeDataStyle> findRange(int[] range);

    int count();

    Integer nextVal();

    public List<CompletudeDataStyle> findAllRange();

}
