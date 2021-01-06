package controllers.unite_organisation;

import entities.Addresse;
import entities.Addresse_;
import entities.Service;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class UniteOgranisationController extends AbstractUniteOgranisationController implements Serializable {
    
    @PostConstruct
    private void init() {
        
    }
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(10L)) {
                signalError("acces_refuse");
                return;
            }            
            mode = "Create";
            service = new Service();
            service.setCentral(false);
            service.setRegional(false);
            service.setVisibilitesuivi(true);
            service.setDatecreation(new Date());
            serviceParent = new Service();
            addresse = new Addresse();
            addresse.setBp("-");
            addresse.setEmail("-");
            addresse.setAdresse("-");
            
            RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareEdit() {
        try {
            if (service != null) {
                
                if (!Utilitaires.isAccess(11L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                if (this.service.getIdparent() != 0) {
                    this.serviceParent = this.serviceFacadeLocal.find(this.service.getIdparent());
                }
                
                addresse = service.getIdaddresse();
                this.mode = "Edit";
                RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public Service findByIdparent(Integer id) {
        Service s = new Service();
        try {
            if (id != 0) {
                s = this.serviceFacadeLocal.find(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public void create() {
        try {
            if (this.mode.equals("Create")) {
                
                this.service.setIdservice(serviceFacadeLocal.nextVal());
                
                if (this.serviceParent.getIdservice() != 0) {
                    this.service.setIdparent(serviceParent.getIdservice());
                } else {
                    service.setIdparent(0);
                }
                
                addresse.setIdaddresse(addresseFacadeLocal.nextVal());
                addresseFacadeLocal.create(addresse);
                service.setIdaddresse(addresse);
                serviceFacadeLocal.create(service);
                
                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement de l'unité d'organisation : " + service.getNom(), SessionMBean.getUserAccount());
                this.service = new Service();
                serviceParent = new Service();
                
                this.detail = this.modifier = this.supprimer = true;
                
                RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
                signalSuccess();
            } else if (this.service != null) {
                
                if (serviceParent.getIdservice() != 0) {
                    service.setIdparent(serviceParent.getIdservice());
                } else {
                    service.setIdparent(0);
                }
                
                addresseFacadeLocal.edit(addresse);
                
                serviceFacadeLocal.edit(service);
                detail = modifier = supprimer = true;
                service = new Service();
                serviceParent = new Service();
                
                RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
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
            if (service != null) {
                
                if (!Utilitaires.isAccess(12L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                if (!serviceFacadeLocal.findByServiceParent(service.getIdservice()).isEmpty()) {
                    signalError("unite_organisation_parent_suppression");
                    return;
                }
                
                this.serviceFacadeLocal.remove(service);
                
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion de l'unité d'organisation : " + this.service.getNom(), SessionMBean.getUserAccount());
                service = new Service();
                detail = this.modifier = supprimer = true;
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
