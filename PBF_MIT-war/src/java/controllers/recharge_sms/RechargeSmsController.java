package controllers.recharge_sms;

import entities.RechargeSms;
import entities.Service;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class RechargeSmsController extends AbstractRechargeSmsController implements Serializable {
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(32L)) {
                signalError("acces_refuse");
                return;
            }
            
            mode = "Create";
            service = new Service();
            rechargeSms = new RechargeSms();
            rechargeSms.setDateRecharge(Date.from(Instant.now()));
            rechargeSms.setNombre(0);
            RequestContext.getCurrentInstance().execute("PF('RechargeCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareEdit() {
        try {
            if (rechargeSms != null) {
                
                if (!Utilitaires.isAccess(32L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                service = rechargeSms.getIdservice();
                this.mode = "Edit";
                RequestContext.getCurrentInstance().execute("PF('RechargeCreerDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void create() {
        try {
            if (mode.equals("Create")) {
                
                ut.begin();
                
                rechargeSms.setIdRechargeSms(rechargeSmsFacadeLocal.nextVal());
                rechargeSms.setIdservice(service);
                rechargeSmsFacadeLocal.create(rechargeSms);
                
                service = serviceFacadeLocal.find(service.getIdservice());
                service.setSoldeSms(service.getSoldeSms() + rechargeSms.getNombre());
                serviceFacadeLocal.edit(service);
                ut.commit();

                //Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'acteur : " + acteur.getNom(), SessionMBean.getUserAccount());
                this.detail = this.modifier = this.supprimer = true;
                
                RequestContext.getCurrentInstance().execute("PF('RechargeCreerDialog').hide()");
                signalSuccess();
            } else if (this.rechargeSms != null) {
                
                ut.begin();
                rechargeSmsFacadeLocal.edit(rechargeSms);
                
                RechargeSms rOld = rechargeSmsFacadeLocal.find(rechargeSms.getIdRechargeSms());
                if (!Objects.equals(rechargeSms.getNombre(), rOld.getNombre())) {
                    rOld.getIdservice().setSoldeSms((rOld.getIdservice().getSoldeSms() - rOld.getNombre()) + rechargeSms.getNombre());
                    serviceFacadeLocal.edit(rOld.getIdservice());
                }
                ut.commit();
                RequestContext.getCurrentInstance().execute("PF('RechargeCreerDialog').hide()");
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void delete() {
        try {
            if (rechargeSms != null) {
                
                if (!Utilitaires.isAccess(32L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                ut.begin();
                rechargeSmsFacadeLocal.remove(rechargeSms);                
                rechargeSms.getIdservice().setSoldeSms(rechargeSms.getIdservice().getSoldeSms() - rechargeSms.getNombre());
                serviceFacadeLocal.edit(rechargeSms.getIdservice());
                ut.commit();

                //Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'acteur " + this.acteur.getNom(), SessionMBean.getUserAccount());
                detail = this.modifier = this.supprimer = true;
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void signalError(String chaine) {
        this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalSuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalException(Exception e) {
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
