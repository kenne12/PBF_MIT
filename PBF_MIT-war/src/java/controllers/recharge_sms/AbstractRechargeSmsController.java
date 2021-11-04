package controllers.recharge_sms;

import entities.RechargeSms;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.MouchardFacadeLocal;
import sessions.RechargeSmsFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

public class AbstractRechargeSmsController {

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
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String mode = "";

    public Boolean getDetail() {
        return this.detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

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
        modifier = supprimer = detail = rechargeSms == null;
    }

    public List<RechargeSms> getRechargeSmss() {
        try {
            rechargeSmss = rechargeSmsFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rechargeSmss;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServices() {
        try {
            services = serviceFacadeLocal.findAllRangeParentOrCtn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public String getMode() {
        return mode;
    }

}
