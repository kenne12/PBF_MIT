/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.promptitude;

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
public class PromptitudeController extends AbstractPromptitudeController implements Serializable {

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

    public double loadValue(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriode(idEtape, idPeriode);
            return val == 0 ? 0 : val.intValue();
        }
        return 0;
    }

    public double loadValueNiveauRegion(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                return val == 0 ? 0 : val.intValue();
            }
        }
        return 0;
    }

    public double loadValueNiveauRegionActeur(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
                return val == 0 ? 0 : val.intValue();
            }
        }
        return 0;
    }

    public double agregateByEtape(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
            if (value != 0) {
                return value.intValue();
            }
        }
        return 0;
    }

    public double agregateByEtapeNiveauRegion(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    public double agregateByEtapeNiveauRegionActeur(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    public double agregateRetardByEtape(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double value = programmationFacadeLocal.getRetardByIdPeriode(periode.getIdperiode());
            if (value != 0) {
                return value.intValue();
            }
        }
        return 0;
    }

    public double agregateRetardByEtapeNiveauRegion(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    public double agregateRetardByEtapeNiveauRegionActeur(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    public double agregatePeriodParent() {
        if (!sousPeriodeFilters.isEmpty()) {
            Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParent(periode.getIdperiode());
            if (valeur != 0) {
                return valeur.intValue();
            }
        }
        return 0;
    }

    public double agregatePeriodParentNiveauRegion() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeur != 0) {
                    return valeur.intValue();
                }
            }
        }
        return 0;
    }

    public double agregatePeriodParentNiveauRegionActeur() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeur != 0) {
                    return valeur.intValue();
                }
            }
        }
        return 0;
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
     * *
     * @param item
     * @return
     *
     */
    public double agregateByEtapeNiveauRegionD(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedService != null && regionDistrict) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    /**
     * *
     * @param periode
     * @return
     *
     */
    public double agregateRetardByEtapeNiveauRegionD(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(periode.getIdperiode(), selectedEtape.getIdetape(), selectedService.getIdservice());
                if (value != 0) {
                    return value.intValue();
                }
            }
        }
        return 0;
    }

    public double agregatePeriodParentNiveauRegionD() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                Double valeur = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedService.getIdservice());
                if (valeur != 0) {
                    return valeur.intValue();
                }
            }
        }
        return 0;
    }

}