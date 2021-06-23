/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "promptitude_data_style")
public class PromptitudeDataStyle implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Column(name = "borne_inferieur")
    private double borneInferieur;
    @Column(name = "borne_superieur")
    private double borneSuperieur;
    @Column(name = "back_ground_color")
    private String backGroundColor;
    @Column(name = "text_color")
    private String textColor;

    public PromptitudeDataStyle() {
    }

    public PromptitudeDataStyle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBorneInferieur() {
        return borneInferieur;
    }

    public void setBorneInferieur(double borneInferieur) {
        this.borneInferieur = borneInferieur;
    }

    public double getBorneSuperieur() {
        return borneSuperieur;
    }

    public void setBorneSuperieur(double borneSuperieur) {
        this.borneSuperieur = borneSuperieur;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PromptitudeDataStyle other = (PromptitudeDataStyle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
