/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.style_promptitude;

import entities.PromptitudeDataStyle;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

/**
 *
 * @author USER
 */
@ManagedBean
@ViewScoped
public class StylepromptitudeController extends AbstractStylepromptitudeController implements Serializable {

    /**
     * Creates a new instance of StylepromptitudeController
     */
    public StylepromptitudeController() {
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(19L)) {
                signalError("acces_refuse");
                return;
            }

            mode = "Create";
            promptitudeDataStyle = new PromptitudeDataStyle();
            RequestContext.getCurrentInstance().execute("PF('StylePromptitudeCreerDialog').show()");
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
            RequestContext.getCurrentInstance().execute("PF('StylePromptitudeCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                promptitudeDataStyle.setId(promptitudeDataStyleFacadeLocal.nextVal());
                promptitudeDataStyleFacadeLocal.create(promptitudeDataStyle);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement des valeurs dashbord de promptitude  : " + promptitudeDataStyle.getBackGroundColor(), SessionMBean.getUserAccount());
                promptitudeDataStyle = new PromptitudeDataStyle();
                RequestContext.getCurrentInstance().execute("PF('StylePromptitudeCreerDialog').hide()");
                signalSuccess();
            } else {
                if (promptitudeDataStyle != null) {
                    promptitudeDataStyleFacadeLocal.edit(promptitudeDataStyle);
                    RequestContext.getCurrentInstance().execute("PF('StylePromptitudeCreerDialog').hide()");
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
            if (promptitudeDataStyle != null) {

                if (!Utilitaires.isAccess(21L)) {
                    signalError("acces_refuse");
                    return;
                }
                promptitudeDataStyleFacadeLocal.remove(promptitudeDataStyle);
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion de l'étape : " + promptitudeDataStyle.getBackGroundColor(), SessionMBean.getUserAccount());
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
