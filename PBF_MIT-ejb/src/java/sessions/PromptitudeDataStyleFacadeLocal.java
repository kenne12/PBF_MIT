/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.PromptitudeDataStyle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface PromptitudeDataStyleFacadeLocal {

    void create(PromptitudeDataStyle promptitudeDataStyle);

    void edit(PromptitudeDataStyle promptitudeDataStyle);

    void remove(PromptitudeDataStyle promptitudeDataStyle);

    PromptitudeDataStyle find(Object id);

    List<PromptitudeDataStyle> findAll();

    List<PromptitudeDataStyle> findRange(int[] range);

    int count();

    public Integer nextVal();

    public List<PromptitudeDataStyle> findAllRange();

}
