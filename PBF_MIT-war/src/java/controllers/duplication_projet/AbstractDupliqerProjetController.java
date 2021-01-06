package controllers.duplication_projet;

import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Periode;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.MouchardFacadeLocal;
import sessions.ActeurFacadeLocal;
import sessions.EtapeFacadeLocal;
import sessions.EtapeprojetFacadeLocal;
import sessions.PeriodeFacadeLocal;
import sessions.ProgrammationFacadeLocal;
import sessions.ProjetFacadeLocal;
import sessions.ProjetserviceFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

public class AbstractDupliqerProjetController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ProjetFacadeLocal projetFacadeLocal;
    protected Projet projet = new Projet();
    protected Projet projet_d = new Projet();
    protected List<Projet> projets = new ArrayList<>();

    @EJB
    protected EtapeFacadeLocal etapeFacadeLocal;
    protected List<Etape> etapes = new ArrayList<>();
    protected List<Etape> selectedEtapes = new ArrayList<>();

    @EJB
    protected EtapeprojetFacadeLocal etapeprojetFacadeLocal;
    protected List<Etapeprojet> etapeprojets = new ArrayList<>();

    @EJB
    protected ActeurFacadeLocal acteurFacadeLocal;
    protected List<Acteur> acteurs = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> services = new ArrayList<>();
    protected List<Service> service_parents = new ArrayList<>();
    protected List<Service> selectedServices = new ArrayList<>();

    @EJB
    protected ProjetserviceFacadeLocal projetserviceFacadeLocal;
    protected List<Projetservice> projetservices = new ArrayList<>();

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> periodes = new ArrayList();

    @EJB
    protected ProgrammationFacadeLocal programmationFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Date dateDebut;

    protected boolean showValidateBtn;
    protected boolean showAddServiceBtn;
    protected boolean showAddEtapeBtn;

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

    public List<Acteur> getActeurs() {
        try {
            acteurs = acteurFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
        mode = "Edit";
        modifier = supprimer = detail = projet == null;
    }

    public List<Projet> getProjets() {
        try {
            projets = projetFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public List<Etape> getEtapes() {
        try {
            etapes = etapeFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etapes;
    }

    public void setEtapes(List<Etape> etapes) {
        this.etapes = etapes;
    }

    public List<Etape> getSelectedEtapes() {
        return selectedEtapes;
    }

    public void setSelectedEtapes(List<Etape> selectedEtapes) {
        this.selectedEtapes = selectedEtapes;
    }

    public List<Etapeprojet> getEtapeprojets() {
        return etapeprojets;
    }

    public void setEtapeprojets(List<Etapeprojet> etapeprojets) {
        this.etapeprojets = etapeprojets;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(List<Service> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public List<Projetservice> getProjetservices() {
        return projetservices;
    }

    public void setProjetservices(List<Projetservice> projetservices) {
        this.projetservices = projetservices;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodes() {
        try {
            periodes = periodeFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return periodes;
    }

    public void setPeriodes(List<Periode> periodes) {
        this.periodes = periodes;
    }

    public boolean isShowValidateBtn() {
        return showValidateBtn;
    }

    public void setShowValidateBtn(boolean showValidateBtn) {
        this.showValidateBtn = showValidateBtn;
    }

    public String getMode() {
        return mode;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getService_parents() {
        try {
            service_parents = serviceFacadeLocal.findAllRangeParent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service_parents;
    }

    public void setService_parents(List<Service> service_parents) {
        this.service_parents = service_parents;
    }

    public boolean isShowAddServiceBtn() {
        return showAddServiceBtn;
    }

    public void setShowAddServiceBtn(boolean showAddServiceBtn) {
        this.showAddServiceBtn = showAddServiceBtn;
    }

    public boolean isShowAddEtapeBtn() {
        return showAddEtapeBtn;
    }

    public void setShowAddEtapeBtn(boolean showAddEtapeBtn) {
        this.showAddEtapeBtn = showAddEtapeBtn;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Projet getProjet_d() {
        return projet_d;
    }

    public void setProjet_d(Projet projet_d) {
        this.projet_d = projet_d;
    }

}
