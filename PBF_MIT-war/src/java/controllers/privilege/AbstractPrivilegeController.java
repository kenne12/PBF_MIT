/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.privilege;

import entities.Menu;

import entities.Privilege;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.model.DualListModel;
import sessions.MenuFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import utils.Routine;

/**
 *
 * @author Gervais
 */
public class AbstractPrivilegeController {

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
    protected List<Privilege> privileges = new ArrayList<>();

    @EJB
    protected MenuFacadeLocal menuFacadeLocal;
    protected List<Menu> menus = new ArrayList<>();
    protected DualListModel<Menu> dualMenu = new DualListModel<>();

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    protected List<Utilisateur> utilisateurs = new ArrayList<>();

    protected Routine routine = new Routine();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected String fileName;

    protected boolean detail = true;
    protected boolean supprimer = true;
    protected boolean imprimer = true;

    public boolean isDetail() {
        return detail;
    }

    public boolean isSupprimer() {
        return supprimer;
    }

    public boolean isImprimer() {
        imprimer = privilegeFacadeLocal.findAll().isEmpty();
        return imprimer;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public DualListModel<Menu> getDualMenu() {
        return dualMenu;
    }

    public void setDualMenu(DualListModel<Menu> dualMenu) {
        this.dualMenu = dualMenu;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        utilisateurs = utilisateurFacadeLocal.findByActif(true);
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}
