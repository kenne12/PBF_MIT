/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs;

import entities.Acteur;
import entities.Service;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.ActeurFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.ServiceFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import utils.Routine;

/**
 *
 * @author kenne
 */
public class AbstractUtilisateurController {

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    protected List<Utilisateur> utilisateurs = new ArrayList<>();

    protected List<Utilisateur> utilisateurActifs = new ArrayList<>();
    protected List<Utilisateur> utilisateurInactifs = new ArrayList<>();

    protected String confirm_password = "";

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> services = new ArrayList<>();

    @EJB
    protected ActeurFacadeLocal acteurFacadeLocal;
    protected Acteur acteur = new Acteur();
    protected List<Acteur> acteurs = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected List<String> templates = new ArrayList<>();

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected Boolean showEditSolde = true;

    protected Boolean showUserCreateDialog = false;
    protected Boolean showUserDetailDialog = false;
    protected Boolean showUserDeleteDialog = false;
    protected Boolean showUserEditDialog = false;
    protected Boolean showUserPrintDialog = false;

    protected boolean buttonActif = false;
    protected boolean buttonInactif = true;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public Boolean getImprimer() {
        imprimer = utilisateurFacadeLocal.findAll().isEmpty();
        return imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Boolean getShowEditSolde() {
        return showEditSolde;
    }

    public void setShowEditSolde(Boolean showEditSolde) {
        this.showEditSolde = showEditSolde;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        modifier = supprimer = detail = utilisateur == null;
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        utilisateurs = utilisateurFacadeLocal.findAll();
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Boolean getShowUserCreateDialog() {
        return showUserCreateDialog;
    }

    public void setShowUserCreateDialog(Boolean showUserCreateDialog) {
        this.showUserCreateDialog = showUserCreateDialog;
    }

    public Boolean getShowUserDetailDialog() {
        return showUserDetailDialog;
    }

    public void setShowUserDetailDialog(Boolean showUserDetailDialog) {
        this.showUserDetailDialog = showUserDetailDialog;
    }

    public Boolean getShowUserDeleteDialog() {
        return showUserDeleteDialog;
    }

    public void setShowUserDeleteDialog(Boolean showUserDeleteDialog) {
        this.showUserDeleteDialog = showUserDeleteDialog;
    }

    public Boolean getShowUserEditDialog() {
        return showUserEditDialog;
    }

    public void setShowUserEditDialog(Boolean showUserEditDialog) {
        this.showUserEditDialog = showUserEditDialog;
    }

    public Boolean getShowUserPrintDialog() {
        return showUserPrintDialog;
    }

    public void setShowUserPrintDialog(Boolean showUserPrintDialog) {
        this.showUserPrintDialog = showUserPrintDialog;
    }

    public List<Utilisateur> getUtilisateurActifs() {
        utilisateurActifs = utilisateurFacadeLocal.findByActif(true);
        return utilisateurActifs;
    }

    public void setUtilisateurActifs(List<Utilisateur> utilisateurActifs) {
        this.utilisateurActifs = utilisateurActifs;
    }

    public List<Utilisateur> getUtilisateurInactifs() {
        utilisateurInactifs = utilisateurFacadeLocal.findByActif(false);
        return utilisateurInactifs;
    }

    public void setUtilisateurInactifs(List<Utilisateur> utilisateurInactifs) {
        this.utilisateurInactifs = utilisateurInactifs;
    }

    public boolean isButtonActif() {
        return buttonActif;
    }

    public void setButtonActif(boolean buttonActif) {
        this.buttonActif = buttonActif;
    }

    public boolean isButtonInactif() {
        return buttonInactif;
    }

    public void setButtonInactif(boolean buttonInactif) {
        this.buttonInactif = buttonInactif;
    }

    public List<String> getTemplates() {
        return templates;
    }

    public Routine getRoutine() {
        return routine;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServices() {
        try {
            services = serviceFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public List<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
