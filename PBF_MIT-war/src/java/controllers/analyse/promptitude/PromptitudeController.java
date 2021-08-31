/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.promptitude;

import entities.Acteur;
import entities.Etape;
import entities.Periode;
import entities.PromptitudeDataStyle;
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
        promptitudeDataStyles = promptitudeDataStyleFacadeLocal.findAllRange();
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

    private String color(double valeur) {
        if (valeur == -1) {
            return "";
        }

        String color = "";
        for (PromptitudeDataStyle c : promptitudeDataStyles) {
            if (valeur >= c.getBorneInferieur() && valeur <= c.getBorneSuperieur()) {
                color = c.getBackGroundColor();
                break;
            }
        }
        return color;
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

    public String agregatePeriodParentNiveauRegionActeur() {
        if (!sousPeriodeFilters.isEmpty()) {
            if (selectedEtape != null && selectedEtape.getIdetape() != null && regionActeur) {
                Double valeur = programmationFacadeLocal.getRetardByIdPeriodeParentIdEtape(periode.getIdperiode(), selectedEtape.getIdetape());
                if (valeur != -1) {
                    return "" + valeur.intValue();
                }
            }
        }
        return "";
    }

    public void initRetardByRegion(Etape e) {
        selectedEtape = e;
        regionRegion = true;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/analyse/promptitude/index.html");
        } catch (Exception ex) {
        }
    }

    public void initRetardByActeur(Etape e) {
        selectedEtape = e;
        regionActeur = true;
        acteurs_finals.clear();
        acteurs.forEach(a -> {
            if (!programmationFacadeLocal.findByIdetapeParentIdActeurIdetape(periode.getIdperiode(), a.getIdacteur(), e.getIdetape()).isEmpty()) {
                acteurs_finals.add(a);
            }
        });
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/analyse/promptitude/index.html");
        } catch (Exception ex) {
        }
    }

    public void initRetardDistrict(Service s) {
        selectedRegion = s;
        regionDistrict = true;
        districts = serviceFacadeLocal.findByServiceWithoutAcv(s.getIdservice());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/analyse/promptitude/index.html");
        } catch (Exception ex) {
        }
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
                Double value = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), periode.getIdperiode(), selectedRegion.getIdservice());
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

    public int toInt(double value) {
        return value == 0 ? 0 : (int) value;
    }

    public void resetModel() {
        this.lineModel = new LineChartModel();
        RequestContext.getCurrentInstance().execute("PF('ModelDataPromptitudeByEtapeCreerDialog').hide()");
    }

    public void promptitudeByDistrict() {
        this.createLineModel("Courbe évolutive de la promptitude des données par District , Etape -> " + selectedEtape.getNom(), this.fillDataPromptitudeByDistrict());
        this.clodeAndDialog();
    }

    public void promptitudeByActeur() {
        this.createLineModel("Courbe évolutive de la promptitude des données par Acteur , Etape -> " + selectedEtape.getNom(), this.fillDataPromptitudeByActeur());
        this.clodeAndDialog();
    }

    public void promptitudeByRegion() {
        this.createLineModel("Courbe évolutive de la promptitude des données par Region , Etape -> " + selectedEtape.getNom(), this.fillDataPromptitudeByRegion());
        this.clodeAndDialog();
    }

    public void promptitudeByEtape() {
        this.createLineModel("Courbe évolutive de la promptitude des données par Etape", this.fillDataPromptitudeByEtape());
        this.clodeAndDialog();
    }

    private void clodeAndDialog() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('ModelDataPromptitudeByEtapeCreerDialog').show()");
    }

    private void createLineModel(String title, LineChartModel lineChartModel) {
        this.lineModel = lineChartModel;
        this.lineModel.setTitle(title);
        this.lineModel.setLegendPosition("e");
        Axis yAxis = this.lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);       
        yAxis.setLabel("Valeur (Jrs)");
        yAxis.setTickInterval("5");
        //yAxis.setTickFormat("%d");
        lineModel.getAxes().put(AxisType.Y, yAxis);

        Axis xAxis = new CategoryAxis("Période");
        xAxis.setTickAngle(-90);
        lineModel.getAxes().put(AxisType.X, xAxis);
    }

    private LineChartModel fillDataPromptitudeByEtape() {
        try {
            LineChartModel model = new LineChartModel();

            etapes.stream().map((e) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(e.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {
                    Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriode(e.getIdetape(), p.getIdperiode());
                    val = val == -1d ? 0d : val;
                    series.set(p.getNom(), val);
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

    private LineChartModel fillDataPromptitudeByRegion() {
        try {
            LineChartModel model = new LineChartModel();

            regions.stream().map((s) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(s.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {
                    Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdserviceParent(selectedEtape.getIdetape(), p.getIdperiode(), s.getIdservice());
                    val = val == -1d ? 0d : val;
                    series.set(p.getNom(), val);
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

    private LineChartModel fillDataPromptitudeByActeur() {
        try {
            LineChartModel model = new LineChartModel();

            acteurs_finals.stream().map((a) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(a.getNom() + " - " + a.getTitre());
                this.sousPeriodeFilters.stream().forEach((p) -> {
                    Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdActeur(selectedEtape.getIdetape(), p.getIdperiode(), a.getIdacteur());
                    val = val == -1 ? 0d : val;
                    series.set(p.getNom(), val);
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

    private LineChartModel fillDataPromptitudeByDistrict() {
        try {
            LineChartModel model = new LineChartModel();

            districts.stream().map((d) -> {
                LineChartSeries serie = new LineChartSeries();
                serie.setLabel(d.getNom());
                this.sousPeriodeFilters.stream().forEach((p) -> {
                    Double val = programmationFacadeLocal.getRetardByIdEtapeIdPeriodeIdservice(selectedEtape.getIdetape(), p.getIdperiode(), d.getIdservice());

                    val = val == -1 ? 0d : val;
                    serie.set(p.getNom(), val);
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
}
