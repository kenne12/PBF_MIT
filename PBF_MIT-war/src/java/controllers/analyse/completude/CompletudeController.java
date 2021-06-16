/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.completude;

import entities.Acteur;
import entities.Etape;
import entities.Periode;
import entities.Service;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
public class CompletudeController extends AbstractCompletudeController implements Serializable {

    @PostConstruct
    private void init() {
        etapes = etapeFacadeLocal.findAllRange();
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
        sousPeriodeFilters.clear();
        sousPeriodeFilters.addAll(sousPeriodes);
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public String loadValue(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(idEtape, idPeriode) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriode(idEtape, idPeriode);
            //return val == 0 ? 0 : val.intValue();
        }
        return "";
    }

    public String loadValueNiveauRegion(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), idPeriode, idServiceParent) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                //return val == 0 ? 0 : val.intValue();
            }
        }
        return "";
    }

    public String loadValueNiveauRegionActeur(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), idPeriode, idActeur) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
                //return val == 0 ? 0 : val.intValue();
            }
        }
        return "";
    }

    public String agregateByEtape(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentValidees(item.getIdetape(), periode.getIdperiode()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
            /*if (value != 0) {
             return value.intValue();
             }*/
        }
        return "";
    }

    public String agregateByEtapeNiveauRegion(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            }
        }
        return "";
    }

    public String agregateByEtapeNiveauRegionActeur(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeurValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
            }
        }
        return "";
    }

    public String agregateRetardByEtape(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            return "" + programmationFacadeLocal.getCompletudeByIdPeriodeValidees(periode.getIdperiode()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriode(periode.getIdperiode());
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauRegion(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
            }
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauRegionActeur(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSize(periode.getIdperiode(), selectedEtape.getIdetape());
            }
        }
        return "";
    }

    public String agregatePeriodParent() {
        if (!sousPeriodeFilters.isEmpty()) {
            return "" + programmationFacadeLocal.getCompletudeByIdPeriodeParentValidees(periode.getIdperiode()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriodeParent(periode.getIdperiode());
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegion() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegionActeur() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSize(periode.getIdperiode(), selectedEtape.getIdetape());
            }
        }
        return "";
    }

    public void initRetardRegion(Etape e) {
        selectedEtape = e;
        regionRegion = true;
    }

    public void initRetardRegionActeur(Etape e) {
        selectedEtape = e;
        regionActeur = true;
    }

    public void initRetardDistrict(Service s) {
        selectedService = s;
        regionDistrict = true;
        serviceDistricts = serviceFacadeLocal.findByServiceParent(s.getIdservice());
    }

    /**
     * @param item
     * @return
     */
    public String agregateByEtapeNiveauRegionD(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedService != null && regionDistrict) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            }
        }
        return "";
    }

    /**
     * @param periode
     * @return
     */
    public String agregateRetardByEtapeNiveauRegionD(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(periode.getIdperiode(), selectedEtape.getIdetape(), selectedService.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(periode.getIdperiode(), selectedEtape.getIdetape(), selectedService.getIdservice());
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegionD() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedService.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedService.getIdservice());
            }
        }
        return "";
    }

}
