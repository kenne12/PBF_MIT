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

        if (sousPeriodes.isEmpty() && services.isEmpty()) {
            JsfUtil.addErrorMessage("Liste des services ou périodes vide");
        }
        sousPeriodeFilters.clear();
        sousPeriodeFilters.addAll(sousPeriodes);
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public String loadValue(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriode(idEtape, idPeriode);
            if (valeurProgrammee == -1) {
                return "";
            }
            return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(idEtape, idPeriode) + " / " + valeurProgrammee;
        }
        return "";
    }

    public String loadValueStyle(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriode(idEtape, idPeriode);
            if (valeurProgrammee == -1) {
                return "";
            }
            int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(idEtape, idPeriode);
            return this.color(valeurValidee, valeurProgrammee);
        }
        return "";
    }

    private String color(int valeurValidee, int valeurProgrammee) {
        if (valeurValidee == 0) {
            return "red";
        }

        double value = (Double.valueOf(valeurValidee) / Double.valueOf(valeurProgrammee)) * 100;
        if (value < 50) {
            return "red";
        } else if (value == 50) {
            return "yellow";
        } else {
            return "#b3e5ec";
        }
    }

    public String loadValueNiveauRegion(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), idPeriode, idServiceParent) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String loadValueNiveauDistrict(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), idPeriode, idService) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String loadValueNiveauDistrictStyle(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), idPeriode, idService);
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String loadValueNiveauRegionStyle(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String loadValueNiveauActeur(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), idPeriode, idActeur) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String loadValueNiveauActeurStyle(int idActeur, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), idPeriode, idActeur);
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), idPeriode, idActeur);
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String agregateByEtape(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentValidees(item.getIdetape(), periode.getIdperiode()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
        }
        return "";
    }

    public String agregateByEtapeStyle(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
            if (valeurProgrammee == 0) {
                return "";
            }
            int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentValidees(item.getIdetape(), periode.getIdperiode());
            return this.color(valeurValidee, valeurProgrammee);
        }
        return "";
    }

    public String agregateByEtapeNiveauRegion(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String agregateByEtapeNiveauRegionStyle(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            if (valeurProgrammee == -1) {
                return "";
            }
            int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            return this.color(valeurValidee, valeurProgrammee);
        }
        return "";
    }

    public String agregateByEtapeNiveauActeur(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeurValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String agregateByEtapeNiveauRegionActeurStyle(Acteur item) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeur(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdActeurValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdacteur());
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String agregateRetardByEtape(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriode(periode.getIdperiode());
            if (valeurProgrammee == -1) {
                return "";
            }
            return "" + programmationFacadeLocal.getCompletudeByIdPeriodeValidees(periode.getIdperiode()) + " / " + valeurProgrammee;
        }
        return "";
    }

    public String agregateRetardByEtapeStyle(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriode(periode.getIdperiode());
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdPeriodeValidees(periode.getIdperiode());
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauRegion(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauActeur(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSize(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String agregatePeriodParent() {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeParent(periode.getIdperiode());
            if (valeurProgrammee != -11) {
                return programmationFacadeLocal.getCompletudeByIdPeriodeParentValidees(periode.getIdperiode()) + " / " + valeurProgrammee;
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegion() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauActeur() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSize(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                }
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

    public String agregateByEtapeNiveauDistrict(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedService != null && regionDistrict) {
                return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            }
        }
        return "";
    }

    public String agregateByEtapeNiveauDistrictStyle(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            if (valeurProgrammee == 0) {
                return "";
            }
            int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
            return this.color(valeurValidee, valeurProgrammee);
        }
        return "";
    }

    public String agregateRetardByEtapeNiveauDistrict(Periode periode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                int valeurPgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(periode.getIdperiode(), selectedEtape.getIdetape(), selectedService.getIdservice());
                if (valeurPgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(periode.getIdperiode(), selectedEtape.getIdetape(), selectedService.getIdservice()) + " / " + valeurPgrammee;
                }
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauDistrict() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedService != null) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedService.getIdservice());
                if (valeurProgrammee != -1) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedService.getIdservice()) + " / " + valeurProgrammee;
                }
            }
        }
        return "";
    }

}
