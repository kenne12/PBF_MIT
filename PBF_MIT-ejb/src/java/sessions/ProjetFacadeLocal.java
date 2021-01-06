/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Projet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ProjetFacadeLocal {

    void create(Projet projet);

    void edit(Projet projet);

    void remove(Projet projet);

    Projet find(Object id);

    List<Projet> findAll();

    List<Projet> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Projet> findAllRange();

    List<Projet> findAllRange(boolean etat);

    List<Projet> findAllRange(boolean etat, boolean cloturee);

}
