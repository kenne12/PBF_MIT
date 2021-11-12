package controllers.analyse.utilisation_sms;

import entities.Notification;
import entities.Periode;
import entities.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

@ManagedBean
@SessionScoped
public class UtilisationSmsController extends AbstractUtilisationSmsController implements Serializable {

    private final String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    private List<Notification> getAllSmsAndMailOuCentral(Service service, Periode periode, int modeSearch) {
        switch (modeSearch) {
            case 1:
                return notificationActeurFacadeLocal.findAllByIdperiodeParentCentralOrgUnit(service.getIdservice(), periode.getIdperiode());
            case 2:
                return notificationActeurFacadeLocal.findAllSmsByIdperiodeParentCentralOrgUnit(service.getIdservice(), periode.getIdperiode());
            case 3:
                return notificationActeurFacadeLocal.findAllMailByIdperiodeParentCentralOrgUnit(service.getIdservice(), periode.getIdperiode());
            default:
                return new ArrayList<>();
        }
    }

    private List<Notification> getAllSmsAndMailOuRegion(Service service, Periode periode, int modeSearch) {
        switch (modeSearch) {
            case 1:
                return notificationActeurFacadeLocal.findAllByIdperiodeParentRegionOrgUnit(service.getIdservice(), periode.getIdperiode());
            case 2:
                return notificationActeurFacadeLocal.findAllSmsByIdperiodeParentRegionOrgUnit(service.getIdservice(), periode.getIdperiode());
            case 3:
                return notificationActeurFacadeLocal.findAllMailByIdperiodeParentRegionOrgUnit(service.getIdservice(), periode.getIdperiode());
            default:
                return new ArrayList<>();
        }
    }

    public void search() {
        try {
            notifications.clear();

            if (periode.getIdperiode() == null || periode.getIdperiode() == 0) {
                JsfUtil.addSuccessMessage("Veuillez sélectionner une période");
                return;
            }

            periode = periodeFacadeLocal.find(periode.getIdperiode());
            listPeriodeFille = periodeFacadeLocal.findByIdParent(periode.getIdperiode());

            if (service.getIdservice() != null && service.getIdservice() != 0) {
                service = serviceFacadeLocal.find(service.getIdservice());
                if (service.getCentral()) {
                    notifications = this.getAllSmsAndMailOuCentral(service, periode, modeSearch);
                } else {
                    notifications = this.getAllSmsAndMailOuRegion(service, periode, modeSearch);
                }
            }

            this.createLineModel("Courbe évolutive de l'utilisation des sms par région", fillData());
            this.redirect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private LineChartModel fillData() {
        try {
            LineChartModel model = new LineChartModel();

            services.stream().map((s) -> {
                LineChartSeries series = new LineChartSeries();
                series.setLabel(s.getNom());
                if (s.getCentral()) {
                    this.listPeriodeFille.stream().forEach((p) -> {
                        Integer val = getAllSmsAndMailOuCentralGraphic(s, p, modeSearch).size();
                        series.set(p.getNom(), val);
                    });
                } else {
                    this.listPeriodeFille.stream().forEach((p) -> {
                        Integer val = getAllSmsAndMailOuRegionGraphic(s, p, modeSearch).size();
                        series.set(p.getNom(), val);
                    });
                }
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

    private List<Notification> getAllSmsAndMailOuCentralGraphic(Service service, Periode periode, int modeSearch) {
        switch (modeSearch) {
            case 1:
                return notificationActeurFacadeLocal.findAllByIdperiodeParentCentralOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            case 2:
                return notificationActeurFacadeLocal.findAllSmsByIdperiodeParentCentralOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            case 3:
                return notificationActeurFacadeLocal.findAllMailByIdperiodeParentCentralOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            default:
                return new ArrayList<>();
        }
    }

    private List<Notification> getAllSmsAndMailOuRegionGraphic(Service service, Periode periode, int modeSearch) {
        switch (modeSearch) {
            case 1:
                return notificationActeurFacadeLocal.findAllByIdperiodeParentRegionOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            case 2:
                return notificationActeurFacadeLocal.findAllSmsByIdperiodeParentRegionOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            case 3:
                return notificationActeurFacadeLocal.findAllMailByIdperiodeParentRegionOrgUnitGraphic(service.getIdservice(), periode.getIdperiode());
            default:
                return new ArrayList<>();
        }
    }

    private void redirect() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/analyse/analyse_sms/index.html");
        } catch (Exception ex) {
        }
    }

    public void signalError(String chaine) {
        this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
