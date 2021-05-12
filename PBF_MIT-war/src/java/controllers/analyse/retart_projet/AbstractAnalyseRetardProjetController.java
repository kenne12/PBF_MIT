/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.retart_projet;

import entities.Periode;
import entities.Projet;
import entities.Projetservice;
import java.util.ArrayList;
import javax.ejb.EJB;
import sessions.ProjetFacadeLocal;
import java.util.List;
import sessions.EtapeprojetFacadeLocal;
import sessions.PeriodeFacadeLocal;
import sessions.ProgrammationFacadeLocal;
import sessions.ProjetserviceFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractAnalyseRetardProjetController {

    @EJB
    protected ProjetFacadeLocal projetFacadeLocal;
    protected Projet projet = new Projet();
    protected List<Projet> projets = new ArrayList<>();

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected List<Periode> periodes = new ArrayList<>();

    @EJB
    protected ProjetserviceFacadeLocal projetserviceFacadeLocal;
    protected Projetservice projetservice = new Projetservice();

    @EJB
    protected EtapeprojetFacadeLocal etapeprojetFacadeLocal;
    
    @EJB
    protected ProgrammationFacadeLocal programmationFacadeLocal;

    protected Routine routine = new Routine();

    public List<Projet> getProjets() {
        return projets;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public List<Periode> getPeriodes() {
        return periodes;
    }

    public void setPeriodes(List<Periode> periodes) {
        this.periodes = periodes;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Projetservice getProjetservice() {
        return projetservice;
    }

    public void setProjetservice(Projetservice projetservice) {
        this.projetservice = projetservice;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}
