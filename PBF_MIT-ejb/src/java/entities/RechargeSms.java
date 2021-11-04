/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "recharge_sms")
public class RechargeSms implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id_recharge_sms")
    private Integer idRechargeSms;
    @JoinColumn(name = "idservice", referencedColumnName = "idservice")
    @ManyToOne(fetch = FetchType.LAZY)
    private Service idservice;
    @Column(name = "date_recharge")
    @Temporal(TemporalType.DATE)
    private Date dateRecharge;
    private int nombre;

    public RechargeSms() {
    }

    public RechargeSms(Integer idRechargeSms) {
        this.idRechargeSms = idRechargeSms;
    }

    public Integer getIdRechargeSms() {
        return idRechargeSms;
    }

    public void setIdRechargeSms(Integer idRechargeSms) {
        this.idRechargeSms = idRechargeSms;
    }

    public Service getIdservice() {
        return idservice;
    }

    public void setIdservice(Service idservice) {
        this.idservice = idservice;
    }

    public Date getDateRecharge() {
        return dateRecharge;
    }

    public void setDateRecharge(Date dateRecharge) {
        this.dateRecharge = dateRecharge;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idRechargeSms);
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
        final RechargeSms other = (RechargeSms) obj;
        if (!Objects.equals(this.idRechargeSms, other.idRechargeSms)) {
            return false;
        }
        return true;
    }

}
