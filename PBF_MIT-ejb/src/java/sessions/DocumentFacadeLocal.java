/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Document;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface DocumentFacadeLocal {

    void create(Document document);

    void edit(Document document);

    void remove(Document document);

    Document find(Object id);

    List<Document> findAll();

    List<Document> findRange(int[] range);

    int count();
    
    Integer nextVal();
    
    List<Document> findAllRange() throws Exception;
    
}
