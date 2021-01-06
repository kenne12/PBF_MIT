/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs;

import entities.Acteur;
import entities.Service;
import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.ShaHash;
import utils.Utilitaires;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class UtilisateurController extends AbstractUtilisateurController implements Serializable {

    /**
     * Creates a new instance of ClientController
     */
    public UtilisateurController() {
    }
    
    @PostConstruct
    private void init() {
        utilisateur = new Utilisateur();
        templates.clear();
    }
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(2L)) {
                signalError("acces_refuse");
                return;
            }
            
            mode = "Create";
            confirm_password = "";
            
            utilisateur = new Utilisateur();
            utilisateur.setEtat(true);
            utilisateur.setPhoto("user_avatar.png");
            
            acteur = new Acteur();
            service = new Service();
            
            RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(3L)) {
                signalError("acces_refuse");
                return;
            }
            
            mode = "Edit";
            acteur = utilisateur.getIdacteur();
            acteurs.clear();
            acteurs.add(acteur);
            service = acteur.getIdservice();
            
            RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void updateActeur() {
        try {
            if (service.getIdservice() != null) {
                List<Utilisateur> utilisateurs = utilisateurFacadeLocal.findByIdservice(service.getIdservice());
                List<Acteur> act_temps = new ArrayList<>();
                for (Utilisateur u : utilisateurs) {
                    act_temps.add(u.getIdacteur());
                }
                
                acteurs = acteurFacadeLocal.findByIdservice(service.getIdservice());
                if (!acteurs.isEmpty()) {
                    if (!act_temps.isEmpty()) {
                        acteurs.removeAll(act_temps);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void create() {
        try {
            if (mode.equals("Create")) {
                
                if (!confirm_password.equals(utilisateur.getPassword())) {
                    signalError("echec_confirmation_password");
                    return;
                }
                
                if (utilisateurFacadeLocal.findByLogin(utilisateur.getLogin()) != null) {
                    signalError("existance_login_message");
                    return;
                }
                
                acteur = acteurFacadeLocal.find(acteur.getIdacteur());
                utilisateur.setIdutilisateur(utilisateurFacadeLocal.nextVal());
                utilisateur.setPassword(new ShaHash().hash(utilisateur.getPassword()));
                utilisateur.setIdacteur(acteur);
                utilisateur.setEtat(true);
                utilisateur.setDatecreation(new Date());
                utilisateurFacadeLocal.create(utilisateur);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement de l'utilisateur : " + acteur.getNom() + " " + acteur.getPrenom(), SessionMBean.getUserAccount());
                utilisateur = new Utilisateur();
                RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').hide()");
                signalSuccess();
            } else {
                if (utilisateur != null) {
                    utilisateurFacadeLocal.edit(utilisateur);
                    RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').hide()");
                    signalSuccess();
                } else {
                    signalError("not_row_selected");
                }
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void reinitialiseAccount(Utilisateur utilisateur) {
        try {
            if (!Utilitaires.isAccess(5L)) {
                signalError("acces_refuse");
                return;
            }
            utilisateur.setPassword(new ShaHash().hash("123456"));
            utilisateurFacadeLocal.edit(utilisateur);
            Utilitaires.saveOperation(mouchardFacadeLocal, "Réinitilisation du compte utilisateur de -> " + utilisateur.getIdacteur().getNom() + " " + utilisateur.getIdacteur().getPrenom(), SessionMBean.getUserAccount());
            signalSuccess();
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void delete() {
        try {
            if (utilisateur != null) {
                
                if (!Utilitaires.isAccess(4L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                mouchardFacadeLocal.deleteByIdUtilisateur(utilisateur.getIdutilisateur());
                privilegeFacadeLocal.deleteByIdUtilisateur(utilisateur.getIdutilisateur());
                utilisateurFacadeLocal.remove(utilisateur);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion de l'utilisateur : " + utilisateur.getIdacteur().getNom() + " " + utilisateur.getIdacteur().getPrenom(), SessionMBean.getUserAccount());
                
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void changeStatus(Utilisateur utilisateur, String mode) {
        try {
            if (mode.equals("activer")) {
                
                if (!Utilitaires.isAccess(6L)) {
                    signalError("acces_refuse");
                    return;
                }
                utilisateur.setEtat(true);
                utilisateurFacadeLocal.edit(utilisateur);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getIdacteur().getNom() + " " + utilisateur.getIdacteur().getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                
                if (!Utilitaires.isAccess(7L)) {
                    signalError("acces_refuse");
                    return;
                }
                utilisateur.setEtat(false);
                utilisateurFacadeLocal.edit(utilisateur);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Désativation du compte de l'utilisateur : " + utilisateur.getIdacteur().getNom() + " " + utilisateur.getIdacteur().getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void signalError(String chaine) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.feedBack("information", "/resources/tool_images/warning.jpeg", routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalSuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.catchException(e, routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
}
