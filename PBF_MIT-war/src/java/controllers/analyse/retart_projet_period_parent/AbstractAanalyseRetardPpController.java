/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.retart_projet_period_parent;

import entities.Periode;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.PeriodeFacadeLocal;
import sessions.ProgrammationFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractAanalyseRetardPpController {

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> periodes = new ArrayList<>();
    protected List<Periode> sousPeriodes = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected Service selectedService = new Service();
    protected List<Service> services = new ArrayList<>();
    protected List<Service> sousServices = new ArrayList<>();

    @EJB
    protected ProgrammationFacadeLocal programmationFacadeLocal;

    protected Routine routine = new Routine();

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServices() {
        try {
            services = serviceFacadeLocal.findAllRangeParent();
        } catch (Exception e) {
        }
        return services;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodes() {
        try {
            periodes = periodeFacadeLocal.findParentPeriod();
        } catch (Exception e) {
        }
        return periodes;
    }

    public Routine getRoutine() {
        return routine;
    }

    public List<Service> getSousServices() {
        return sousServices;
    }

    public List<Periode> getSousPeriodes() {
        return sousPeriodes;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

}
