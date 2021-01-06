/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.mouchard;

import entities.Mouchard;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;

/**
 *
 * @author kenne
 */
public class AbstractMouchardController {

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Mouchard mouchard;
    protected List<Mouchard> mouchards = new ArrayList<>();

    public List<Mouchard> getMouchards() {
        mouchards = mouchardFacadeLocal.findAll();
        return mouchards;
    }

    public void setMouchards(List<Mouchard> mouchards) {
        this.mouchards = mouchards;
    }

}
