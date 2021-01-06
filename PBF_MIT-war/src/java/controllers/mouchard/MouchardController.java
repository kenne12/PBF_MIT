/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.mouchard;

import entities.Mouchard;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class MouchardController extends AbstractMouchardController {

    /**
     * Creates a new instance of ClientController
     */
    public MouchardController() {
    }

    @PostConstruct
    private void init() {
        mouchard = new Mouchard();
    }

}
