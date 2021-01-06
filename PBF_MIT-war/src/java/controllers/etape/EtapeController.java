/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.etape;

import entities.Document;
import entities.Etape;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class EtapeController extends AbstractEtapeController implements Serializable {

    /**
     * Creates a new instance of ClientController
     */
    public EtapeController() {
    }

    @PostConstruct
    private void init() {

    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(19L)) {
                signalError("acces_refuse");
                return;
            }

            mode = "Create";
            etape = new Etape();
            document = new Document();
            RequestContext.getCurrentInstance().execute("PF('EtapeCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(20L)) {
                signalError("acces_refuse");
                return;
            }

            mode = "Edit";
            document = etape.getIddocument();
            RequestContext.getCurrentInstance().execute("PF('EtapeCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                etape.setIdetape(etapeFacadeLocal.nextVal());
                etape.setIddocument(document);

                String dossier = etape.getRepertoire().replaceAll(" ", "_");
                dossier = dossier.replaceAll("é", "e");
                dossier = dossier.replaceAll("__", "_");
                dossier = dossier.toLowerCase();
                etape.setRepertoire(dossier);
                etapeFacadeLocal.create(etape);

                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement de l'étape  : " + etape.getNom(), SessionMBean.getUserAccount());
                etape = new Etape();
                RequestContext.getCurrentInstance().execute("PF('EtapeCreerDialog').hide()");
                signalSuccess();
            } else {
                if (etape != null) {
                    etape.setIddocument(documentFacadeLocal.find(document.getIddocument()));
                    String dossier = etape.getRepertoire().replaceAll(" ", "_");
                    dossier = dossier.replaceAll("é", "e");
                    dossier = dossier.replaceAll("__", "_");
                    etape.setRepertoire(dossier);
                    etapeFacadeLocal.edit(etape);
                    RequestContext.getCurrentInstance().execute("PF('EtapeCreerDialog').hide()");
                    signalSuccess();
                } else {
                    signalError("not_row_selected");
                }
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (etape != null) {

                if (!Utilitaires.isAccess(21L)) {
                    signalError("acces_refuse");
                    return;
                }
                etapeFacadeLocal.remove(etape);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion de létape : " + etape.getNom(), SessionMBean.getUserAccount());
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
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
