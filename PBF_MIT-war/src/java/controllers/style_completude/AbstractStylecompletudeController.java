/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.style_completude;

import entities.CompletudeDataStyle;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.CompletudeDataStyleFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractStylecompletudeController {

    @EJB
    protected CompletudeDataStyleFacadeLocal completudeDataStyleFacadeLocal;
    protected CompletudeDataStyle completudeDataStyle = new CompletudeDataStyle();
    protected List<CompletudeDataStyle> completudeDataStyles = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean supprimer = true;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Routine getRoutine() {
        return routine;
    }

    public CompletudeDataStyle getCompletudeDataStyle() {
        return completudeDataStyle;
    }

    public void setCompletudeDataStyle(CompletudeDataStyle completudeDataStyle) {
        this.completudeDataStyle = completudeDataStyle;
        modifier = supprimer = detail = completudeDataStyle == null;
    }

    public List<CompletudeDataStyle> getCompletudeDataStyles() {
        completudeDataStyles = completudeDataStyleFacadeLocal.findAllRange();
        return completudeDataStyles;
    }

}
