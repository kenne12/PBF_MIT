package controllers.suivi;

import entities.Acteur;
import entities.Document;
import entities.Etape;
import entities.Etapeprojet;
import entities.Piecejointes;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.MouchardFacadeLocal;
import sessions.ActeurFacadeLocal;
import sessions.DocumentFacadeLocal;
import sessions.EtapeFacadeLocal;
import sessions.EtapeprojetFacadeLocal;
import sessions.PiecejointesFacadeLocal;
import sessions.ProgrammationFacadeLocal;
import sessions.ProjetFacadeLocal;
import sessions.ProjetserviceFacadeLocal;
import sessions.ServiceFacadeLocal;
import sessions.TraitementFacadeLocal;
import utils.Routine;

public class AbstractSuiviController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ProgrammationFacadeLocal programmationFacadeLocal;
    protected Programmation programmation = new Programmation();
    protected List<Programmation> programmations = new ArrayList<>();
    protected List<Programmation> programmationList = new ArrayList<>();

    @EJB
    protected PiecejointesFacadeLocal piecejointesFacadeLocal;
    protected Piecejointes piecejointe = new Piecejointes();
    protected List<Piecejointes> piecejointes = new ArrayList<>();

    @EJB
    protected TraitementFacadeLocal traitementFacadeLocal;

    @EJB
    protected ProjetFacadeLocal projetFacadeLocal;
    protected Projet projet = new Projet();
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
    protected List<Service> services = new ArrayList<>();
    protected List<Service> selectedServices = new ArrayList<>();

    @EJB
    protected ProjetserviceFacadeLocal projetserviceFacadeLocal;
    protected Projetservice projetservice = new Projetservice();
    protected List<Projetservice> projetservices = new ArrayList<>();

    @EJB
    protected DocumentFacadeLocal documentFacadeLocal;
    protected List<Document> documents = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected boolean showValidateBtn;
    protected boolean disabledDownPiece = true;

    protected Routine routine = new Routine();

    protected String filename = "";
    protected String type_fichier = "";

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
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

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
        modifier = supprimer = detail = projet == null;
    }

    public List<Projet> getProjets() {
        try {
            projets = projetFacadeLocal.findAllRange(true);
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

    public boolean isShowValidateBtn() {
        return showValidateBtn;
    }

    public void setShowValidateBtn(boolean showValidateBtn) {
        this.showValidateBtn = showValidateBtn;
    }

    public String getMode() {
        return mode;
    }

    public List<Programmation> getProgrammations() {
        return programmations;
    }

    public void setProgrammations(List<Programmation> programmations) {
        this.programmations = programmations;
    }

    public Projetservice getProjetservice() {
        return projetservice;
    }

    public void setProjetservice(Projetservice projetservice) {
        this.projetservice = projetservice;
    }

    public List<Document> getDocuments() {
        try {
            documents = documentFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Programmation> getProgrammationList() {
        return programmationList;
    }

    public void setProgrammationList(List<Programmation> programmationList) {
        this.programmationList = programmationList;
    }

    public Programmation getProgrammation() {
        return programmation;
    }

    public void setProgrammation(Programmation programmation) {
        this.programmation = programmation;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Piecejointes> getPiecejointes() {
        return piecejointes;
    }

    public void setPiecejointes(List<Piecejointes> piecejointes) {
        this.piecejointes = piecejointes;
    }

    public Piecejointes getPiecejointe() {
        return piecejointe;
    }

    public void setPiecejointe(Piecejointes piecejointe) {
        this.piecejointe = piecejointe;
    }

    public String getType_fichier() {
        return type_fichier;
    }

    public void setType_fichier(String type_fichier) {
        this.type_fichier = type_fichier;
    }

    public boolean isDisabledDownPiece() {
        return disabledDownPiece;
    }

    public void setDisabledDownPiece(boolean disabledDownPiece) {
        this.disabledDownPiece = disabledDownPiece;
    }

}
