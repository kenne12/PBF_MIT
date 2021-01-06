package controllers.unite_organisation;

import entities.Addresse;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.AddresseFacadeLocal;
import sessions.MouchardFacadeLocal;

import sessions.ServiceFacadeLocal;
import utils.Routine;

public class AbstractUniteOgranisationController {

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> services = new ArrayList<>();

    protected Service serviceParent = new Service();
    protected List<Service> serviceParents = new ArrayList<>();

    @EJB
    protected AddresseFacadeLocal addresseFacadeLocal;
    protected Addresse addresse = new Addresse();

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

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        modifier = supprimer = detail = service == null;
    }

    public List<Service> getServices() {
        try {
            services = serviceFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getServiceParent() {
        return serviceParent;
    }

    public void setServiceParent(Service serviceParent) {
        this.serviceParent = serviceParent;
    }

    public List<Service> getServiceParents() {
        try {
            serviceParents = serviceFacadeLocal.findParentService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceParents;
    }

    public void setServiceParents(List<Service> serviceParents) {
        this.serviceParents = serviceParents;
    }

    public Addresse getAddresse() {
        return addresse;
    }

    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }

}
