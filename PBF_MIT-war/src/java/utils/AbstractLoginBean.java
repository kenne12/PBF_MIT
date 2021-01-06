/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Mouchard;
import entities.Parametrage;

import java.util.Date;
import javax.ejb.EJB;
import sessions.MenuFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.ParametrageFacadeLocal;

import sessions.PrivilegeFacadeLocal;

/**
 *
 * @author gervais
 */
public class AbstractLoginBean {

    @EJB
    protected MenuFacadeLocal menuFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    
    @EJB
    protected ParametrageFacadeLocal parametrageFacadeLocal;
    protected Parametrage parametrage = new Parametrage();

    protected Mouchard mouchard;

    protected Routine routine = new Routine();

    protected Date date = new Date();

    protected String confirmPassword = "";

    //visibility module
    protected boolean showHibernatePanel = false;

    protected String hibernatePassword = "";

    //Session boolean
    protected boolean showSessionPanel = true;

    protected boolean connected = false;

    public boolean isShowHibernatePanel() {
        return showHibernatePanel;
    }

    public void setShowHibernatePanel(boolean showHibernatePanel) {
        this.showHibernatePanel = showHibernatePanel;
    }

    public String getHibernatePassword() {
        return hibernatePassword;
    }

    public void setHibernatePassword(String hibernatePassword) {
        this.hibernatePassword = hibernatePassword;
    }

    public boolean isShowSessionPanel() {
        return showSessionPanel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Routine getRoutine() {
        return routine;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
