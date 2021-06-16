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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.ActeurFacadeLocal;
import sessions.EtapeFacadeLocal;
import sessions.PeriodeFacadeLocal;
import sessions.ProgrammationFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractCompletudeController {

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> periodes = new ArrayList<>();
    protected List<Periode> sousPeriodes = new ArrayList<>();
    protected List<Periode> sousPeriodeFilters = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected Service selectedService = new Service();
    protected Service selectedServiceDistrict = new Service();
    protected List<Service> services = new ArrayList<>();
    protected List<Service> sousServices = new ArrayList<>();
    protected List<Service> serviceDistricts = new ArrayList<>();

    @EJB
    protected ActeurFacadeLocal acteurFacadeLocal;
    protected Acteur acteur = new Acteur();
    protected Acteur selectedActeur = new Acteur();
    protected List<Acteur> acteurs = new ArrayList<>();

    @EJB
    protected EtapeFacadeLocal etapeFacadeLocal;
    protected Etape etape = new Etape();
    protected Etape selectedEtape = new Etape();
    protected List<Etape> etapes = new ArrayList<>();

    protected boolean regionRegion = false;
    protected boolean regionActeur = false;
    protected boolean regionDistrict = false;

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
        services = serviceFacadeLocal.findAllRangeParentOrCtn();
        return services;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodes() {
        periodes = periodeFacadeLocal.findParentPeriod();
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

    public List<Periode> getSousPeriodeFilters() {
        return sousPeriodeFilters;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public Etape getSelectedEtape() {
        return selectedEtape;
    }

    public void setSelectedEtape(Etape selectedEtape) {
        this.selectedEtape = selectedEtape;
    }

    public List<Etape> getEtapes() {
        return etapes;
    }

    public boolean isRegionRegion() {
        return regionRegion;
    }

    public boolean isRegionActeur() {
        return regionActeur;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public Acteur getSelectedActeur() {
        return selectedActeur;
    }

    public void setSelectedActeur(Acteur selectedActeur) {
        this.selectedActeur = selectedActeur;
    }

    public List<Acteur> getActeurs() {
        acteurs = acteurFacadeLocal.findAllRange();
        return acteurs;
    }

    public List<Service> getServiceDistricts() {
        return serviceDistricts;
    }

    public Service getSelectedServiceDistrict() {
        return selectedServiceDistrict;
    }

    public void setSelectedServiceDistrict(Service selectedServiceDistrict) {
        this.selectedServiceDistrict = selectedServiceDistrict;
    }

    public boolean isRegionDistrict() {
        return regionDistrict;
    }

    public void setRegionDistrict(boolean regionDistrict) {
        this.regionDistrict = regionDistrict;
    }

}
