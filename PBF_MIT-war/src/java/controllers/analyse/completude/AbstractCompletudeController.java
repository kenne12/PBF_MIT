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
    protected List<Periode> periodeParents = new ArrayList<>();
    protected List<Periode> sousPeriodes = new ArrayList<>();
    protected List<Periode> sousPeriodeFilters = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> regions = new ArrayList<>();
    protected Service selectedRegion = new Service();
    protected List<Service> sousServices = new ArrayList<>();
    protected List<Service> districts = new ArrayList<>();
    protected Service selectedDistrict = new Service();

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

    public List<Service> getRegions() {
        regions = serviceFacadeLocal.findAllRangeParentOrCtn();
        return regions;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodeParents() {
        periodeParents = periodeFacadeLocal.findParentPeriod();
        return periodeParents;
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

    public Service getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Service selectedRegion) {
        this.selectedRegion = selectedRegion;
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

    public List<Service> getDistricts() {
        return districts;
    }

    public Service getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(Service selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    public boolean isRegionDistrict() {
        return regionDistrict;
    }

    public void setRegionDistrict(boolean regionDistrict) {
        this.regionDistrict = regionDistrict;
    }

}
