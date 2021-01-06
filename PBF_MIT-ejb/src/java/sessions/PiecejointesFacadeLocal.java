/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Piecejointes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface PiecejointesFacadeLocal {

    void create(Piecejointes piecejointes);

    void edit(Piecejointes piecejointes);

    void remove(Piecejointes piecejointes);

    Piecejointes find(Object id);

    List<Piecejointes> findAll();

    List<Piecejointes> findRange(int[] range);

    int count();

    Long nextVal();

    List<Piecejointes> findByIdprogrammation(long idprogrammation) throws Exception;

    List<Piecejointes> findByIdprojetservice(long idprojetservice) throws Exception;

    void deleteByIdprojetservice(long idprojetservice) throws Exception;

    void deleteByIdprojet(int idprojet) throws Exception;

}
