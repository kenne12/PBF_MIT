package controllers.duplication_projet;

import entities.Periode;
import entities.Projet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.MouchardFacadeLocal;
import sessions.EtapeprojetFacadeLocal;
import sessions.NotificationActeurFacadeLocal;
import sessions.NotificationFacadeLocal;
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
    protected EtapeprojetFacadeLocal etapeprojetFacadeLocal;

    @EJB
    protected ServiceFacadeLocal serviceFacadeLocal;

    @EJB
    protected ProjetserviceFacadeLocal projetserviceFacadeLocal;

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> periodes = new ArrayList();

    @EJB
    protected ProgrammationFacadeLocal programmationFacadeLocal;

    @EJB
    protected NotificationFacadeLocal notificationFacadeLocal;

    @EJB
    protected NotificationActeurFacadeLocal notificationActeurFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Date dateDebut;

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

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
        mode = "Edit";
        modifier = supprimer = detail = projet == null;
    }

    public List<Projet> getProjets() {
        projets = projetFacadeLocal.findAllRange();
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodes() {
        periodes = periodeFacadeLocal.findAllRange();
        return periodes;
    }

    public String getMode() {
        return mode;
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
