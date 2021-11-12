package controllers.analyse.utilisation_sms;

import entities.Notification;
import entities.Periode;
import entities.RechargeSms;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.LineChartModel;
import sessions.MouchardFacadeLocal;
import sessions.NotificationActeurFacadeLocal;
import sessions.NotificationFacadeLocal;
import sessions.PeriodeFacadeLocal;
import sessions.RechargeSmsFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

public class AbstractUtilisationSmsController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected RechargeSmsFacadeLocal rechargeSmsFacadeLocal;
    protected RechargeSms rechargeSms = new RechargeSms();
    protected List<RechargeSms> rechargeSmss = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> services = new ArrayList<>();

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> listPeriodeParent = new ArrayList<>();
    protected List<Periode> listPeriodeFille = new ArrayList<>();

    @EJB
    protected NotificationFacadeLocal notificationFacadeLocal;
    protected Notification notification = new Notification();
    protected List<Notification> notifications = new ArrayList<>();

    @EJB
    protected NotificationActeurFacadeLocal notificationActeurFacadeLocal;

    protected LineChartModel lineModel = new LineChartModel();

    protected Integer modeSearch = 1;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Routine getRoutine() {
        return this.routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public RechargeSms getRechargeSms() {
        return rechargeSms;
    }

    public void setRechargeSms(RechargeSms rechargeSms) {
        this.rechargeSms = rechargeSms;
    }

    public List<RechargeSms> getRechargeSmss() {
        return rechargeSmss;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServices() {
        services = serviceFacadeLocal.findAllRangeParentOrCtn();
        return services;
    }

    public String getMode() {
        return mode;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getListPeriodeParent() {
        listPeriodeParent = periodeFacadeLocal.findParentPeriod();
        return listPeriodeParent;
    }

    public List<Periode> getListPeriodeFille() {
        return listPeriodeFille;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Integer getModeSearch() {
        return modeSearch;
    }

    public void setModeSearch(Integer modeSearch) {
        this.modeSearch = modeSearch;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
