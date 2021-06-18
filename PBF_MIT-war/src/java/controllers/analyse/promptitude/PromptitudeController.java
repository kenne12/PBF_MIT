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
        try {
            Periode p = periodeFacadeLocal.findParentPeriodDefault();
            if (p != null) {
                periode = p;
                sousPeriodeFilters = periodeFacadeLocal.findByIdParent(periode.getIdperiode());
            }
        } catch (Exception e) {
        }
    }

    public void searchData() {
        if (periode.getIdperiode() == null) {
            JsfUtil.addErrorMessage("Période nulle");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        //sousServices = serviceFacadeLocal.findByServiceParent(service.getIdservice(), false, false);
        sousPeriodes = periodeFacadeLocal.findByIdParent(periode.getIdperiode());

        if (sousPeriodes.isEmpty() && regions.isEmpty()) {
            JsfUtil.addErrorMessage("Liste des services ou périodes vide");
        }
        sousPeriodeFilters.clear();
        sousPeriodeFilters.addAll(sousPeriodes);
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    private String color(double valeur) {
        if (valeur == -1) {
            return "";
        }

        if (valeur < 20) {
            return "#b3e5ec";
        } else if (valeur >= 20 && valeur < 50) {
            return "yellow";
        } else {
            return "red";
        }
    }

    public String loadValueByEtape(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriode(idEtape, idPeriode);
            return val == -1 ? "" : "" + val.intValue();
        }
        return "";
    }

    public String loadValueByEtapeStyle(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriode(idEtape, idPeriode);
            return this.color(val);
        }
        return "";
    }

    public String loadValueByRegion(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                return val == -1d ? "" : "" + val.intValue();
            }
        }
        return "";
    }

    public String loadValueByRegionStyle(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                return color(value);
            }
        }
        return "";
    }

    public String loadValueDistrict(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
                return val == -1d ? "" : "" + val.intValue();
            }
        }
        return "";
    }

    public String loadValueDistrictStyle(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
                return color(value);
            }
        }
        return "";
    }

    public String loadValueActeur(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
                return val == -1 ? "" : "" + val.intValue();
            }
        }
        return "";
    }

    public String loadValueActeurStyle(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
                return color(value);
            }
        }
        return "";
    }

    public String agregateByEtape(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
            return value == -1 ? "" : "" + value.intValue();
        }
        return "";
    }

    public String agregateByEtapeStyle(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double valeur = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
            return color(valeur);
        }
        return "";
    }

    public String agregateByEtapeNiveauRegion(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                return value == -1d ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregateByEtapeNiveauRegionStyle(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                return color(value);
            }
        }
        return "";
    }

    public String agregateByEtapeActeur(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
                return value == -1 ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregateByEtapeActeurStyle(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
                return color(value);
            }
        }
        return "";
    }

    public String agregateRetardByEtape(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            Double value = programmationFacadeLocal.getRetardByIdPeriode(periode.getIdperiode());
            return value == -1 ? "" : "" + value.intValue();
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauRegion(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double value = programmationFacadeLocal.getRetardByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                return value == -1 ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauRegionActeur(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double value = programmationFacadeLocal.getRetardByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                return value == -1 ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregatePeriodParent() {
        if (!sousPeriodeFilters.isEmpty()) {
            Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParent(periode.getIdperiode());
            return valeur == -1 ? "" : "" + valeur.intValue();
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegion() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                return valeur == -1 ? "" : "" + valeur.intValue();
            }
        }
        return "";
    }

    public int agregatePeriodParentNiveauRegionActeur() {
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

    public void initRetardByRegion(Etape e) {
        selectedEtape = e;
        regionRegion = true;
    }

    public void initRetardByActeur(Etape e) {
        selectedEtape = e;
        regionActeur = true;
    }

    public void initRetardDistrict(Service s) {
        selectedRegion = s;
        regionDistrict = true;
        districts = serviceFacadeLocal.findByServiceParent(s.getIdservice());
    }

    public String agregateByEtapeDistrict(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedRegion != null && regionDistrict) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                return value == -1d ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregateByEtapeDistrictStyle(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedRegion != null && regionDistrict) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                return color(value);
            }
        }
        return "";
    }

    public String agregateRetardByEtapeDistrict(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedRegion != null) {
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(periode.getIdperiode(), selectedEtape.getIdetape(), selectedRegion.getIdservice());
                return value == -1d ? "" : "" + value.intValue();
            }
        }
        return "";
    }

    public String agregatePeriodParentDistrict() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedRegion != null) {
                Double valeur = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice());
                return valeur == -1d ? "" : "" + valeur.intValue();
            }
        }
        return "";
    }

}
