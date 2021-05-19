/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.retart_projet_period_parent;

import entities.Periode;
import entities.Service;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;

/**
 *
 * @author USER
 */
@ManagedBean
@SessionScoped
public class AnalyseRetardPpController extends AbstractAanalyseRetardPpController implements Serializable {

    /**
     * Creates a new instance of AanalyseRetardPpCpntroller
     */
    public AnalyseRetardPpController() {
    }

    public void searchData() {
        if (service.getIdservice() == null || periode.getIdperiode() == null) {
            JsfUtil.addErrorMessage("Service ou periode nulle");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        sousServices = serviceFacadeLocal.findByServiceParent(service.getIdservice(), false, false);
        sousPeriodes = periodeFacadeLocal.findByIdParent(periode.getIdperiode());

        if (sousPeriodes.isEmpty() && sousServices.isEmpty()) {
            JsfUtil.addErrorMessage("Liste des services ou périodes vide");
        }
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public double loadValue(int idService, int idPeriode) {
        if (!sousPeriodes.isEmpty() && !sousServices.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceIdPeriode(idService, idPeriode);
        }
        return 0;
    }

    public double agregate(Service item) {
        if (!sousPeriodes.isEmpty() && !sousServices.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceIdPeriodeParent(item.getIdservice(), periode.getIdperiode());
        }
        return 0;
    }

    public double agregateRetardByEtape(Periode periode) {
        if (!sousPeriodes.isEmpty() && !sousServices.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceParentIdperiode(service.getIdservice(), periode.getIdperiode());
        }
        return 0;
    }

    public double agregateRetardByProjet() {
        if (!sousPeriodes.isEmpty() && !sousServices.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceParentIdperiodeParent(service.getIdservice(), periode.getIdperiode());
        }
        return 0;
    }

}
