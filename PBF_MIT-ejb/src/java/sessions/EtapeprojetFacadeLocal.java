/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Etapeprojet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface EtapeprojetFacadeLocal {

    void create(Etapeprojet etapeprojet);

    void edit(Etapeprojet etapeprojet);

    void remove(Etapeprojet etapeprojet);

    Etapeprojet find(Object id);

    List<Etapeprojet> findAll();

    List<Etapeprojet> findRange(int[] range);

    int count();

    Long nextVal();

    List<Etapeprojet> findByIdprojet(int idprojet);

    void deleteByIdprojet(int idprojet) throws Exception;
}
