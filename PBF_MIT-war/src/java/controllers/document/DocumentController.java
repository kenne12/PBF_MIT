/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.document;

import entities.Document;
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
public class DocumentController extends AbstractDocumentController implements Serializable {

    /**
     * Creates a new instance of ClientController
     */
    public DocumentController() {
    }

    @PostConstruct
    private void init() {

    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(16L)) {
                signalError("acces_refuse");
                return;
            }

            mode = "Create";
            document = new Document();
            RequestContext.getCurrentInstance().execute("PF('DocumentCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(17L)) {
                signalError("acces_refuse");
                return;
            }

            mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('DocumentCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                document.setIddocument(documentFacadeLocal.nextVal());
                documentFacadeLocal.create(document);

                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement du document  : " + document.getNom(), SessionMBean.getUserAccount());
                document = new Document();
                RequestContext.getCurrentInstance().execute("PF('DocumentCreerDialog').hide()");
                signalSuccess();
            } else {
                if (document != null) {
                    documentFacadeLocal.edit(document);
                    RequestContext.getCurrentInstance().execute("PF('DocumentCreerDialog').hide()");
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
            if (document != null) {

                if (!Utilitaires.isAccess(18L)) {
                    signalError("acces_refuse");
                    return;
                }
                documentFacadeLocal.remove(document);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppression du document : " + document.getNom(), SessionMBean.getUserAccount());
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
