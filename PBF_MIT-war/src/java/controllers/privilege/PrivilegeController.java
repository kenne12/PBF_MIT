/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.privilege;

import entities.Menu;
import entities.Privilege;
import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

/**
 *
 * @author Gervais
 */
@ManagedBean
@ViewScoped
public class PrivilegeController extends AbstractPrivilegeController implements PrivilegeInterfaceController, Serializable {

    @PostConstruct
    private void initAcces() {
        utilisateur = new Utilisateur();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(9L)) {
                signalError("acces_refuse");
                return;
            }
            dualMenu.getSource().clear();
            dualMenu.getTarget().clear();
            RequestContext.getCurrentInstance().execute("PF('AccesCreerDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAccess(Utilisateur utilisateur) {
        try {
            this.utilisateur = utilisateur;
            privileges = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur());
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void handleUserChange() {
        dualMenu.getSource().clear();
        dualMenu.getTarget().clear();
        try {
            if (utilisateur.getIdutilisateur() != null) {

                utilisateur = utilisateurFacadeLocal.find(utilisateur.getIdutilisateur());

                List<Privilege> privilegeTemp = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur());
                if (privilegeTemp.isEmpty()) {
                    dualMenu.setSource(menuFacadeLocal.findAll());
                } else {

                    List<Menu> menusTarget = new ArrayList<>();

                    for (Privilege p : privilegeTemp) {
                        menusTarget.add(p.getIdmenu());
                    }

                    dualMenu.getTarget().addAll(menusTarget);
                    dualMenu.getSource().addAll(menuFacadeLocal.findAll());
                    dualMenu.getSource().removeAll(menusTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            if (utilisateur.getIdutilisateur() != null) {
                utilisateur = utilisateurFacadeLocal.find(utilisateur.getIdutilisateur());

                for (Menu m : dualMenu.getSource()) {
                    Privilege p = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur(), m.getIdmenu());
                    if (p != null) {
                        privilegeFacadeLocal.remove(p);
                        Utilitaires.saveOperation(mouchardFacadeLocal, "RETRAIT DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    }
                }

                Boolean flag = false;
                for (Menu m : dualMenu.getTarget()) {

                    if (flag == false) {

                        if (m.getIdmenu() == 1) {
                            flag = true;
                            Privilege p = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur(), 1);
                            if (p == null) {
                                p = new Privilege();
                                p.setIdprivilege(privilegeFacadeLocal.nextVal());
                                p.setIdmenu(m);
                                p.setIdutilisateur(utilisateur);
                                privilegeFacadeLocal.create(p);
                                Utilitaires.saveOperation(mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE : SUPER ADMINISTRATEUR à l'utilisateur -> " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                                break;
                            } else {
                                JsfUtil.addSuccessMessage("Vous disposez dejà du privilège SUPER ADMINISTRATEUR");
                                break;
                            }
                        } else {
                            Privilege p = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur(), m.getIdmenu());
                            if (p == null) {
                                p = new Privilege();
                                p.setIdprivilege(privilegeFacadeLocal.nextVal());
                                p.setIdmenu(m);
                                p.setIdutilisateur(utilisateur);
                                privilegeFacadeLocal.create(p);
                                Utilitaires.saveOperation(mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                            }
                        }
                    }
                }

                signalSuccess();

            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    private void signalError(String chaine) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.feedBack("information", "/resources/tool_images/warning.jpeg", routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    private void signalSuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    private void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        routine.catchException(e, routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

}
