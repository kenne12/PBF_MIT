/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.completude;

import entities.Acteur;
import entities.CompletudeDataStyle;
import entities.Etape;
import entities.Periode;
import entities.Service;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import utils.JsfUtil;
import utils.Utilitaires;

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
        completudeDataStyles = completudeDataStyleFacadeLocal.findAllRange();
        acteurs = acteurFacadeLocal.findAllRange();
        try {
            Periode p = periodeFacadeLocal.findParentPeriodDefault();
            if (p != null) {
                periode = p;
                sousPeriodeFilters = periodeFacadeLocal.findByIdParent(periode.getIdperiode());
            }
        } catch (Exception e) {
        }
    }

    public void initOption(String link) {
        if (option.equals("%")) {
            option = "val";
        } else {
            option = "%";
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + link);
        } catch (Exception ex) {
        }
    }

    public void searchData() {
        if (periode.getIdperiode() == null) {
            JsfUtil.addErrorMessage("Période nulle");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        sousPeriodes = periodeFacadeLocal.findByIdParent(periode.getIdperiode());

        if (sousPeriodes.isEmpty() && regions.isEmpty()) {
            JsfUtil.addErrorMessage("Liste des services ou périodes vide");
        }
        sousPeriodeFilters.clear();
        sousPeriodeFilters.addAll(sousPeriodes);
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public String loadValueByEtape(int idEtape, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriode(idEtape, idPeriode);
            if (valeurProgrammee == -1) {
                return "";
            }
            if (option.equals("val")) {
                return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(idEtape, idPeriode) + " / " + valeurProgrammee;
            }

            if (option.equals("%")) {
                double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(idEtape, idPeriode)), valeurProgrammee);
                return "" + Utilitaires.arrondiNDecimales(val, 2);
            }
        }
        return "";
    }

    public String loadValueByEtapeStyle(int idEtape, int idPeriode) {
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

        String color = "";
        double value = (Double.valueOf(valeurValidee) / Double.valueOf(valeurProgrammee)) * 100;
        for (CompletudeDataStyle c : completudeDataStyles) {
            if (value >= c.getBorneInferieur() && value <= c.getBorneSuperieur()) {
                color = c.getBackGroundColor();
                break;
            }
        }
        return color;
    }

    public String loadValueByRegion(long idServiceParent, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), idPeriode, idServiceParent);
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), idPeriode, idServiceParent) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), idPeriode, idServiceParent)), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public String loadValueByDistrict(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), idPeriode, idService) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), idPeriode, idService)), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public String loadValueByDistrictStyle(long idService, int idPeriode) {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), idPeriode, idService);
            if (valeurProgrammee != -1) {
                int valeurValidee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), idPeriode, idService);
                return this.color(valeurValidee, valeurProgrammee);
            }
        }
        return "";
    }

    public String loadValueByRegionStyle(long idServiceParent, int idPeriode) {
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
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), idPeriode, idActeur) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), idPeriode, idActeur)), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
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

    public String agregateValueByEtape(Etape item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (option.equals("val")) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
                return "" + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentValidees(item.getIdetape(), periode.getIdperiode()) + " / " + valeurProgrammee;
            }

            if (option.equals("%")) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParent(item.getIdetape(), periode.getIdperiode());
                double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentValidees(item.getIdetape(), periode.getIdperiode())), valeurProgrammee);
                return "" + Utilitaires.arrondiNDecimales(val, 2);
            }
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

    public String agregateByEtapeByRegion(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public String agregateByEtapeByRegionStyle(Service item) {
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

            if (option.equals("val")) {
                return "" + programmationFacadeLocal.getCompletudeByIdPeriodeValidees(periode.getIdperiode()) + " / " + valeurProgrammee;
            }

            if (option.equals("%")) {
                double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeValidees(periode.getIdperiode())), valeurProgrammee);
                return "" + Utilitaires.arrondiNDecimales(val, 2);
            }
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
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
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
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public String agregatePeriodParent() {
        if (!sousPeriodeFilters.isEmpty()) {
            int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeParent(periode.getIdperiode());
            if (valeurProgrammee != -11) {

                if (option.equals("val")) {
                    return programmationFacadeLocal.getCompletudeByIdPeriodeParentValidees(periode.getIdperiode()) + " / " + valeurProgrammee;
                }

                if (option.equals("%")) {
                    double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeParentValidees(periode.getIdperiode())), valeurProgrammee);
                    return "" + Utilitaires.arrondiNDecimales(val, 2);
                }
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauRegion() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionRegion) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeValidees(periode.getIdperiode(), selectedEtape.getIdetape())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
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
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdPeriodeParentIdEtapeSizeValidees(periode.getIdperiode(), selectedEtape.getIdetape())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public void initRetardByRegion(Etape e, String link) {
        selectedEtape = e;
        regionRegion = true;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + link);
        } catch (Exception ex) {
        }
    }

    public void initRetardByActeur(Etape e, String link) {
        selectedEtape = e;
        regionActeur = true;
        acteurs_finals.clear();
        acteurs.forEach(a -> {
            if (!programmationFacadeLocal.findByIdetapeParentIdActeurIdetape(periode.getIdperiode(), a.getIdacteur(), e.getIdetape()).isEmpty()) {
                acteurs_finals.add(a);
            }
        });
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + link);
        } catch (Exception ex) {
        }
    }

    public void initRetardDistrict(Service s, String link) {
        selectedRegion = s;
        regionDistrict = true;
        districts = serviceFacadeLocal.findByServiceWithoutAcv(s.getIdservice());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + link);
        } catch (Exception ex) {
        }
    }

    public String agregateByEtapeNiveauDistrict(Service item) {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && selectedRegion != null && regionDistrict) {
                if (option.equals("val")) {
                    return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice()) + " / " + programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                }

                if (option.equals("%")) {
                    int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdservice(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice());
                    double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceValidees(selectedEtape.getIdetape(), periode.getIdperiode(), item.getIdservice())), valeurProgrammee);
                    return "" + Utilitaires.arrondiNDecimales(val, 2);
                }
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
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedRegion != null) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice());
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public String agregatePeriodParentNiveauDistrict() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionDistrict && selectedRegion != null) {
                int valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice());
                if (valeurProgrammee != -1) {
                    if (option.equals("val")) {
                        return programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice()) + " / " + valeurProgrammee;
                    }

                    if (option.equals("%")) {
                        double val = this.calculPercentage(((double) programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeParentIdserviceParentValidees(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice())), valeurProgrammee);
                        return "" + Utilitaires.arrondiNDecimales(val, 2);
                    }
                }
            }
        }
        return "";
    }

    public int toInt(double value) {
        return value == 0 ? 0 : (int) value;
    }

    public void completudeByDistrict() {
        this.createLineModel("Courbe évolutive de la complétude des données par District , Etape -> " + selectedEtape.getNom(), this.fillDataCompletudeByDistrict());
        this.closeAndOpenDialog();
    }

    public void completudeByActeur() {
        this.createLineModel("Courbe évolutive de la complétude des données par Acteur , Etape -> " + selectedEtape.getNom(), this.fillDataCompletudeByActeur());
        this.closeAndOpenDialog();
    }

    public void completudeByRegion() {
        this.createLineModel("Courbe évolutive de la complétude des données par Region , Etape -> " + selectedEtape.getNom(), this.fillDataCompletudeByRegion());
        this.closeAndOpenDialog();
    }

    public void completudeByEtape() {
        this.createLineModel("Courbe évolutive de la complétude des données par Etape", this.fillDataCompletudeByEtape());
        this.closeAndOpenDialog();
    }

    private void createLineModel(String title, LineChartModel lineChartModel) {
        this.lineModel = lineChartModel;
        this.lineModel.setTitle(title);
        this.lineModel.setLegendPosition("e");

        //this.lineModel.setBreakOnNull(false);
        Axis yAxis = lineModel.getAxes().get(AxisType.Y);
        yAxis.setLabel("Valeur (%)");
        yAxis.setMin(0d);
        yAxis.setMax(100d);
        yAxis.setTickInterval("5");
        yAxis.setTickFormat("%d");
        lineModel.getAxes().put(AxisType.Y, yAxis);

        Axis xAxis = new CategoryAxis("Période");
        xAxis.setTickAngle(-90);
        lineModel.getAxes().put(AxisType.X, xAxis);
    }

    private double calculPercentage(double validee, double programme) {
        return (validee / programme) * 100;
    }

    private LineChartModel fillDataCompletudeByEtape() {
        try {
            LineChartModel model = new LineChartModel();

            etapes.stream().map((e) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(e.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {
                    double valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriode(e.getIdetape(), p.getIdperiode());
                    if (valeurProgrammee == -1) {
                        series.set(p.getNom(), 50);
                    } else {
                        double val = this.calculPercentage(programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeValidees(e.getIdetape(), p.getIdperiode()), valeurProgrammee);
                        series.set(p.getNom(), val);
                    }
                });
                return series;
            }).forEach((series) -> {
                model.addSeries((ChartSeries) series);
            });
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    private LineChartModel fillDataCompletudeByRegion() {
        try {
            LineChartModel model = new LineChartModel();

            regions.stream().map((s) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(s.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {

                    double valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), p.getIdperiode(), s.getIdservice());
                    if (valeurProgrammee == -1) {
                        series.set(p.getNom(), 50);
                    } else {
                        double val = this.calculPercentage(programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceParentValidees(selectedEtape.getIdetape(), p.getIdperiode(), s.getIdservice()), valeurProgrammee);
                        series.set(p.getNom(), val);
                    }
                });
                return series;
            }).forEach((series) -> {
                model.addSeries((ChartSeries) series);
            });
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    private LineChartModel fillDataCompletudeByActeur() {
        try {
            LineChartModel model = new LineChartModel();

            acteurs_finals.stream().map((a) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(a.getNom() + " - " + a.getTitre());
                this.sousPeriodeFilters.stream().forEach((p) -> {

                    double valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), p.getIdperiode(), a.getIdacteur());
                    if (valeurProgrammee == -1) {
                        series.set(p.getNom(), 50);
                    } else {
                        double val = this.calculPercentage(programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdActeurValidees(selectedEtape.getIdetape(), p.getIdperiode(), a.getIdacteur()), valeurProgrammee);
                        series.set(p.getNom(), val);
                    }
                });
                return series;
            }).forEach((series) -> {
                model.addSeries((ChartSeries) series);
            });
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    private LineChartModel fillDataCompletudeByDistrict() {
        try {
            LineChartModel model = new LineChartModel();

            districts.stream().map((d) -> {
                LineChartSeries serie = new LineChartSeries();
                serie.setLabel(d.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {

                    double valeurProgrammee = programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), p.getIdperiode(), d.getIdservice());
                    if (valeurProgrammee == -1) {
                        serie.set(p.getNom(), 50);
                    } else {
                        double val = this.calculPercentage(programmationFacadeLocal.getCompletudeByIdEtapeIdPeriodeIdserviceValidees(selectedEtape.getIdetape(), p.getIdperiode(), d.getIdservice()), valeurProgrammee);
                        serie.set(p.getNom(), val);
                    }
                });
                return serie;
            }).forEach((series) -> {
                model.addSeries((ChartSeries) series);
            });
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    public void resetModel() {
        this.lineModel = new LineChartModel();
        RequestContext.getCurrentInstance().execute("PF('ModelDataCompletudeDialog').hide()");
    }

    private void closeAndOpenDialog() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('ModelDataCompletudeDialog').show()");
    }

}
