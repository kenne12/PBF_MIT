package controllers.periode;

import entities.Periode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;
import sessions.PeriodeFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import utils.Routine;

public class AbstractPeriodeController {

    @EJB
    protected PeriodeFacadeLocal periodeFacadeLocal;
    protected Periode periode = new Periode();
    protected Periode periodeParent = new Periode();
    protected List<Periode> periodes = new ArrayList();
    protected List<Periode> periodes1 = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
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

    public Periode getPeriode() {
        return this.periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
        this.modifier = this.supprimer = this.detail = periode == null;
    }

    public List<Periode> getPeriodes() {
        try {
            this.periodes = this.periodeFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.periodes;
    }

    public void setPeriodes(List<Periode> periodes) {
        this.periodes = periodes;
    }

    public List<Periode> getPeriodes1() {
        try {
            this.periodes1 = this.periodeFacadeLocal.findParentPeriod();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.periodes1;
    }

    public void setPeriodes1(List<Periode> periodes1) {
        this.periodes1 = periodes1;
    }

    public Periode getPeriodeParent() {
        return this.periodeParent;
    }

    public void setPeriodeParent(Periode periodeParent) {
        this.periodeParent = periodeParent;
    }

}
