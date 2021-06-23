/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.style_promptitude;

import entities.PromptitudeDataStyle;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.PromptitudeDataStyleFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractStylepromptitudeController {

    @EJB
    protected PromptitudeDataStyleFacadeLocal promptitudeDataStyleFacadeLocal;
    protected PromptitudeDataStyle promptitudeDataStyle = new PromptitudeDataStyle();
    protected List<PromptitudeDataStyle> promptitudeDataStyles = new ArrayList<>();

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

    public PromptitudeDataStyle getPromptitudeDataStyle() {
        return promptitudeDataStyle;
    }

    public void setPromptitudeDataStyle(PromptitudeDataStyle promptitudeDataStyle) {
        this.promptitudeDataStyle = promptitudeDataStyle;
        modifier = supprimer = detail = promptitudeDataStyle == null;
    }

    public List<PromptitudeDataStyle> getPromptitudeDataStyles() {
        try {
            promptitudeDataStyles = promptitudeDataStyleFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promptitudeDataStyles;
    }

}
