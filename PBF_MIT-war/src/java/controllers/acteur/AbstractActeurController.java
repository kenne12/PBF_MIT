package controllers.acteur;

import entities.Addresse;
import entities.Acteur;
import entities.Document;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.AddresseFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.ActeurFacadeLocal;
import sessions.DocumentFacadeLocal;
import sessions.ServiceFacadeLocal;
import utils.Routine;

public class AbstractActeurController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ActeurFacadeLocal acteurFacadeLocal;
    protected Acteur acteur = new Acteur();
    protected List<Acteur> acteurs = new ArrayList<>();

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;
    protected Service service = new Service();
    protected List<Service> services = new ArrayList<>();

    @EJB
    protected DocumentFacadeLocal documentFacadeLocal;
    protected Document document = new Document();
    protected List<Document> documents = new ArrayList<>();

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

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
        modifier = supprimer = detail = acteur == null;
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

    public Addresse getAddresse() {
        return addresse;
    }

    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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

}
