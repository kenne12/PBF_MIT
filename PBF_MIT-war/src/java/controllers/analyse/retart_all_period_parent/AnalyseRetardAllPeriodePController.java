/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.retart_all_period_parent;

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
public class AnalyseRetardAllPeriodePController extends AbstractAnalyseRetardAllPeriodePController implements Serializable {

    /**
     * Creates a new instance of AnalyseRetardAllPeriodePController
     */
    public AnalyseRetardAllPeriodePController() {
    }

    public void searchData() {
        if (periode.getIdperiode() == null) {
            JsfUtil.addErrorMessage("Période nulle");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        //sousServices = serviceFacadeLocal.findByServiceParent(service.getIdservice(), false, false);
        sousPeriodes = periodeFacadeLocal.findByIdParent(periode.getIdperiode());

        if (sousPeriodes.isEmpty() && services.isEmpty()) {
            JsfUtil.addErrorMessage("Liste des services ou périodes vide");
        }
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public double loadValue(int idService, int idPeriode) {
        if (!sousPeriodes.isEmpty() && !services.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceParentIdPeriode(idService, idPeriode);
        }
        return 0;
    }

    public double agregate(Service item) {
        if (!sousPeriodes.isEmpty() && !services.isEmpty()) {
            return programmationFacadeLocal.getRetardByIdServiceParentIdPeriodeParent(item.getIdservice(), periode.getIdperiode());
        }
        return 0;
    }

    public double agregateRetardByEtape(Periode periode) {
        if (!sousPeriodes.isEmpty() && !services.isEmpty()) {

            Double value = 0D;
            for (Service s : services) {
                value += programmationFacadeLocal.getRetardByIdServiceParentIdPeriode(s.getIdservice(), periode.getIdperiode());
            }
            return value;
        }
        return 0;
    }

    public double agregateRetardByProjet() {
        if (!sousPeriodes.isEmpty() && !services.isEmpty()) {
            Double value = 0D;
            for (Service s : services) {
                value += programmationFacadeLocal.getRetardByIdServiceParentIdPeriodeParent(s.getIdservice(), periode.getIdperiode());
            }
            return value;
        }
        return 0;
    }

}
