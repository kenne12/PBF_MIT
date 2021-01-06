/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Traitement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface TraitementFacadeLocal {

    void create(Traitement traitement);

    void edit(Traitement traitement);

    void remove(Traitement traitement);

    Traitement find(Object id);

    List<Traitement> findAll();

    List<Traitement> findRange(int[] range);

    int count();
    
}
